# The topic launcher uses the same demo list as run.sh.
$ErrorActionPreference = 'Stop'
$choice = 'list'
if ($args.Count -gt 0) { $choice = $args[0] }
if ($args.Count -gt 1) {
    [Console]::Error.WriteLine('Pass program arguments to the individual demo runner.')
    exit 2
}
$exitCode = 0
Push-Location -LiteralPath $PSScriptRoot
try {
    $names = @(Get-Content -LiteralPath demos.txt -Encoding UTF8 |
        Where-Object { $_.Trim().Length -gt 0 })
    if ($choice -eq 'list') {
        $names | ForEach-Object { Write-Output $_ }
    }
    else {
        $mode = 'run'
        if ($choice -eq 'test') { $mode = 'test' }
        if ($choice -in @('all', 'test')) {
            $selected = $names
        }
        elseif ($names -contains $choice) {
            $selected = @($choice)
        }
        else {
            [Console]::Error.WriteLine('Use: run.cmd [list|all|test|a demo name from list]')
            exit 2
        }
        foreach ($name in $selected) {
            Write-Output "`n--- $name ---"
            # A child script's exit code is checked before starting another example.
            & (Join-Path $PSScriptRoot "$name/run.ps1") $mode
            if ($LASTEXITCODE -ne 0) {
                $exitCode = $LASTEXITCODE
                break
            }
        }
    }
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    $exitCode = 1
}
finally {
    Pop-Location
}
exit $exitCode
