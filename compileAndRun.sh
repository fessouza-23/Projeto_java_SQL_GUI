#!/bin/bash

# Descomentar e (remover o *) das linhas com * caso o JDK não esteja nas variaveis do sistema


# Limpa o diretorio out para evitar conflitos com builds antigas
rm -rf out
mkdir out

# Adiciona o caminho para o JDK caso nao esteja definido
# * if [ -z "$JDK_PATH" ]; then
    # * JDK_PATH="/usr/lib/jvm/java-17-openjdk-amd64"
# * fi

# Compila os arquivos java
# * "$JDK_PATH/bin/javac" -d out src/*.java src/controller/*.java src/domain/*.java src/repository/*.java src/view/*.java

javac -d out src/*.java src/controller/*.java src/domain/*.java src/repository/*.java src/view/*.java


# Verifica se a compilacao ocorreu normalmente
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    read -p "Press any key to continue..."
    exit 1
fi

# Executa a aplicacao java compilada
# * "$JDK_PATH/bin/java" -cp out:lib/hsqldb.jar Main
java -cp out:lib/hsqldb.jar Main