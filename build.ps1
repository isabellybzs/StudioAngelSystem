$ErrorActionPreference = "Stop"

$jdkCandidates = @()

if ($env:JAVA_HOME) {
    $jdkCandidates += $env:JAVA_HOME
}

$jdkCandidates += @(
    "D:\Program Files\Android\Android Studio\jbr",
    "C:\Program Files\Java\latest"
)

$jdkHome = $null
foreach ($candidate in $jdkCandidates) {
    if ($candidate -and (Test-Path (Join-Path $candidate "bin\javac.exe"))) {
        $jdkHome = $candidate
        break
    }
}

if (-not $jdkHome) {
    Write-Error "Nenhum JDK foi encontrado. Instale um JDK ou configure JAVA_HOME apontando para uma pasta com bin\javac.exe."
}

$env:JAVA_HOME = $jdkHome
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

Write-Host "Usando JAVA_HOME=$env:JAVA_HOME"
mvn package "-Dmaven.test.skip=true"
