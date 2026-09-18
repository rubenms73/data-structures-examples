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
    $prefix = @()
    if (Get-Command py -ErrorAction SilentlyContinue) {
        $python = 'py'
        $prefix = @('-3')
    }
    elseif (Get-Command python -ErrorAction SilentlyContinue) { $python = 'python' }
    else { throw 'Install Python 3.10 or newer and make py or python available on PATH.' }
    if ($mode -eq 'test') { & $python @prefix -B -m unittest -v test_stack }
    else { & $python @prefix -B main.py }
    $exitCode = $LASTEXITCODE
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    $exitCode = 1
}
finally { Pop-Location }
exit $exitCode
