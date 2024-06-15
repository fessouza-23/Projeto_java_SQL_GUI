#!/bin/bash

# Limpa o diretório out para evitar conflitos com builds antigas
rm -rf out
mkdir out

# Tenta encontrar o caminho do JDK automaticamente
if [ -z "$JDK_PATH" ]; then
    if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
        JDK_PATH="/usr/lib/jvm/java-17-openjdk-amd64"
    elif [ -d "/usr/lib/jvm/java-17-openjdk" ]; then
        JDK_PATH="/usr/lib/jvm/java-17-openjdk"
    else
        echo "Caminho do JDK não encontrado automaticamente. Por favor, insira o caminho do JDK:"
        read -p "Caminho do JDK: " JDK_PATH
    fi
fi

# Verifica se o JDK_PATH é válido
if [ ! -d "$JDK_PATH" ]; then
    echo "Caminho do JDK inválido: $JDK_PATH"
    exit 1
fi

# Compila os arquivos java
"$JDK_PATH/bin/javac" -d out src/*.java src/controller/*.java src/domain/*.java src/repository/*.java src/view/*.java src/service/*.java

# Verifica se a compilação ocorreu normalmente
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    read -p "Press any key to continue..."
    exit 1
fi

# Executa a aplicação java compilada
"$JDK_PATH/bin/java" -cp out:lib/hsqldb.jar Main

