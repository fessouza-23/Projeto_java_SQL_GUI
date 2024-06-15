@echo off
cls

rem Descomentar e (remover o *) das linhas com * caso o JDK não esteja nas variaveis do sistema

rem Limpa o diretorio out para evitar conflitos com builds antigas
rmdir /S /Q out
mkdir out

rem Utilizar caso o JDK não esteja nas variaveis do sistema
rem Adiciona o caminho para o JDK caso nao esteja definido
rem * IF NOT DEFINED JDK_PATH (
    rem * SET "JDK_PATH=%ProgramFiles%\Java\jdk-17"
rem * )

rem Compila os arquivos java
rem * "%JDK_PATH%\bin\javac" -d out src/*.java src/controller/*.java src/domain/*.java src/repository/*.java src/view/*.java src/service/*.java
javac -d out src/*.java src/controller/*.java src/domain/*.java src/repository/*.java src/view/*.java src/service/*.java

rem Verifica se a compilacao ocorreu normalmente
IF ERRORLEVEL 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

rem Executa a aplicacao java compilada
rem * "%JDK_PATH%\bin\java" -cp out;lib/hsqldb.jar Main
java -cp out;lib/hsqldb.jar Main