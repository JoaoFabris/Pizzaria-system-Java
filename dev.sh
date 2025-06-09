#!/bin/bash
clear
echo "🍕 PIZZA DEV TOOLKIT"
echo "===================="

case "$1" in
    "watch"|"w")
        echo "👀 Iniciando modo watch..."
        ./watch.sh
        ;;
    "build"|"b")
        echo "🔄 Compilando..."
        javac -d . Projeto/*.java
        if [ $? -eq 0 ]; then
            echo "✅ Compilação OK!"
        else
            echo "❌ Erro na compilação!"
        fi
        ;;
    "run"|"r")
        echo "🚀 Executando..."
        java Projeto.Pizzaria
        ;;
    "clean"|"c")
        echo "🧹 Limpando arquivos .class..."
        find Projeto/ -name "*.class" -delete
        echo "✅ Arquivos .class removidos!"
        ;;
    "dev"|"d"|"")
        echo "🔄 Compilando..."
        javac -d . Projeto/*.java
        if [ $? -eq 0 ]; then
            echo "✅ Compilação OK!"
            echo "🚀 Executando..."
            echo "===================="
            java Projeto.Pizzaria
        else
            echo "❌ Erro na compilação!"
        fi
        ;;
    *)
        echo "Uso: ./dev.sh [opção]"
        echo ""
        echo "Opções:"
        echo "  (vazio)  - Compilar e executar (padrão)"
        echo "  dev/d    - Compilar e executar"
        echo "  build/b  - Apenas compilar"
        echo "  run/r    - Apenas executar"
        echo "  watch/w  - Modo watch (recompila automaticamente)"
        echo "  clean/c  - Limpar arquivos .class"
        ;;
esac
