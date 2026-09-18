$ErrorActionPreference = 'Stop'
$mode = 'run'
if ($args.Count -gt 0) { $mode = $args[0] }
if ($args.Count -gt 1 -or $mode -notin @('run', 'test')) {
    [Console]::Error.WriteLine('Use: run.cmd [run|test]')
    exit 2
}
$exitCode = 1
Push-Location -LiteralPath $PSScriptRoot
try {
    if (-not (Get-Command dotnet -ErrorAction SilentlyContinue)) {
        throw 'Install the .NET 8 SDK and make dotnet available on PATH.'
    }
    & dotnet build --nologo --verbosity quiet
    if ($LASTEXITCODE -ne 0) { $exitCode = $LASTEXITCODE }
    else {
        $programArguments = @()
        if ($mode -eq 'test') { $programArguments = @('test') }
        & dotnet 'bin/Debug/net8.0/StackExample.dll' @programArguments
        $exitCode = $LASTEXITCODE
    }
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    $exitCode = 1
}
finally { Pop-Location }
exit $exitCode
