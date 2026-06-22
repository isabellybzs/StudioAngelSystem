@echo off
setlocal
cd /d "%~dp0"

where java >nul 2>nul
if errorlevel 1 (
    echo Java nao encontrado.
    echo Instale o Java 8 ou superior, ou abra pelo NetBeans/Eclipse.
    pause
    exit /b 1
)

if not exist "StudioAngelSystem.jar" (
    echo Arquivo StudioAngelSystem.jar nao encontrado nesta pasta.
    echo Gere o executavel novamente pelo arquivo gerar-executavel.ps1.
    pause
    exit /b 1
)

java -jar "StudioAngelSystem.jar"
pause
