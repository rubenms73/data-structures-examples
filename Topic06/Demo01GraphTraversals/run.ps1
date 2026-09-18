# Works with Windows PowerShell 5.1 and PowerShell 7. Run via run.cmd on Windows.
$ErrorActionPreference = 'Stop'
$mode = 'run'
$programArguments = @()
if ($args.Count -gt 0) { $mode = $args[0] }
if ($args.Count -gt 1) { $programArguments = @($args[1..($args.Count - 1)]) }
if ($mode -notin @('run', 'test')) {
    [Console]::Error.WriteLine('Use: run.cmd [run|test] [program arguments]')
    exit 2
}
if ($mode -eq 'test' -and $programArguments.Count -ne 0) {
    [Console]::Error.WriteLine('The test command does not accept program arguments.')
    exit 2
}
$exitCode = 1
$previousEncoding = [Console]::OutputEncoding
Push-Location -LiteralPath $PSScriptRoot
try {
    [Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
    if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
        throw 'JDK 17 or newer is required. Add its bin directory to PATH (javac and java).'
    }
    if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
        throw 'Java is not on PATH. Use the same JDK for javac and java.'
    }
    New-Item -ItemType Directory -Path bin -Force | Out-Null
    Get-ChildItem -LiteralPath bin -Filter '*.class' -Recurse -File |
        Remove-Item -Force
    $sourceFiles = @(Get-ChildItem -LiteralPath src -Filter '*.java' -Recurse -File)
    if ($mode -eq 'test') {
        $sourceFiles += @(Get-ChildItem -LiteralPath tests -Filter '*.java' -Recurse -File)
    }
    # Relative, quoted paths work even when the project path contains spaces/accents.
    $sourceLines = @($sourceFiles | Sort-Object FullName | ForEach-Object {
        '"' + $_.FullName.Substring($PSScriptRoot.Length + 1).Replace('\', '/') + '"'
    })
    $sourceList = Join-Path $PSScriptRoot 'bin/sources.txt'
    [System.IO.File]::WriteAllLines($sourceList, [string[]]$sourceLines,
        [System.Text.UTF8Encoding]::new($false))
    $classPathEntries = @('bin')
    if (Test-Path -LiteralPath lib) {
        $classPathEntries += @(Get-ChildItem -LiteralPath lib -Filter '*.jar' -File |
            Sort-Object Name | ForEach-Object { $_.FullName })
    }
    $classPath = $classPathEntries -join [System.IO.Path]::PathSeparator
    $release = '17'
    if ($env:JAVA_RELEASE) { $release = $env:JAVA_RELEASE }
    $compilerArguments = @('-J-Dfile.encoding=UTF-8', '--release', $release,
        '-encoding', 'UTF-8', '-Xlint:all', '-Werror', '-cp', $classPath,
        '-d', 'bin', '@bin/sources.txt')
    & javac @compilerArguments
    if ($LASTEXITCODE -ne 0) {
        $exitCode = $LASTEXITCODE
    }
    else {
        $mainClass = 'app.Main'
        if ($mode -eq 'test') { $mainClass = 'tests.ExampleChecks' }
        & java '-Dfile.encoding=UTF-8' -cp $classPath $mainClass @programArguments
        $exitCode = $LASTEXITCODE
    }
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    $exitCode = 1
}
finally {
    [Console]::OutputEncoding = $previousEncoding
    Pop-Location
}
exit $exitCode
