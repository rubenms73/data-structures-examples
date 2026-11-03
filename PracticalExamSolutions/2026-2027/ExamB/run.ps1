param([ValidateSet("run", "test")][string]$Action = "run")
$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)
Push-Location $PSScriptRoot
try
{
    New-Item -ItemType Directory -Force bin | Out-Null
    $Sources = @(Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
    & javac '-J-Dfile.encoding=UTF-8' '-J-Dstdout.encoding=UTF-8' '-J-Dstderr.encoding=UTF-8' -encoding UTF-8 -cp "lib/*" -d bin @Sources
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    if ($Action -eq "test")
    {
        & java '-Dfile.encoding=UTF-8' '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' -cp "bin;lib/*" org.junit.runner.JUnitCore tests.ReadOnlySortedCollectionTest
    }
    else
    {
        & java '-Dfile.encoding=UTF-8' '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' -cp "bin;lib/*" app.App
    }
    exit $LASTEXITCODE
}
finally
{
    Pop-Location
}
