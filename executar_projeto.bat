@echo off
echo Compilando o projeto...

javac -cp ".;Bibliotecas\mysql-connector-j-9.3.0.jar" ManagerProject\*.java

if %errorlevel% neq 0 (
    echo.
    echo Houve erro na compilação.
    pause
    exit /b
)

echo.
echo Compilação bem-sucedida!
echo Iniciando o sistema...
echo.

java -cp ".;Bibliotecas\mysql-connector-j-9.3.0.jar" ManagerProject.CodigoPrincipal

echo.
pause
