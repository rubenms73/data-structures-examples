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
    $sourceFile = 'main.cpp'
    if ($mode -eq 'test') { $sourceFile = 'tests.cpp' }
    New-Item -ItemType Directory -Path bin -Force | Out-Null
    $compiler = $env:CXX
    if (-not $compiler) {
        if (Get-Command cl -ErrorAction SilentlyContinue) { $compiler = 'cl' }
        elseif (Get-Command g++ -ErrorAction SilentlyContinue) { $compiler = 'g++' }
        else { throw 'Use a Visual Studio Developer terminal (cl), or put g++ on PATH.' }
    }
    if ([System.IO.Path]::GetFileNameWithoutExtension($compiler) -eq 'cl') {
        & $compiler /nologo /std:c++17 /EHsc /W4 /WX $sourceFile /Fobin/stack.obj /Febin/stack.exe
    }
    else {
        & $compiler -std=c++17 -Wall -Wextra -Wpedantic -Werror $sourceFile -o bin/stack.exe
    }
    if ($LASTEXITCODE -ne 0) { $exitCode = $LASTEXITCODE }
    else {
        & (Join-Path $PSScriptRoot 'bin/stack.exe')
        $exitCode = $LASTEXITCODE
    }
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    $exitCode = 1
}
finally { Pop-Location }
exit $exitCode
