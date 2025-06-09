Sistema de Pizzaria - Pizza Dev Toolkit
Um sistema completo de gerenciamento de pizzaria desenvolvido em Java, com funcionalidades para pedidos, clientes, cardápio e análise de vendas.

📋 Índice
Sobre o Projeto
Funcionalidades
Estrutura do Projeto
Como Executar
Classes Principais
Exemplos de Uso
Tecnologias
Contribuição
🎯 Sobre o Projeto
O Pizza Dev Toolkit é um sistema de gerenciamento para pizzarias que permite:

Cadastro e gerenciamento de clientes
Criação e alteração de pedidos
Cálculo automático de frete baseado em distância
Análise de combinações de sabores mais populares
Relatórios de vendas
⚡ Funcionalidades
🛒 Gestão de Pedidos
✅ Fazer novos pedidos
✅ Adicionar/remover pizzas de pedidos existentes
✅ Alterar sabores de pizzas
✅ Cálculo automático de preços e frete
👥 Gestão de Clientes
✅ Cadastro de novos clientes
✅ Armazenamento de distância para cálculo de frete
✅ Listagem completa de clientes
🍕 Cardápio Inteligente
✅ Preços dinâmicos baseados na quantidade de sabores
✅ Diferentes tamanhos: Broto, Média, Grande, Giga
✅ 10+ sabores disponíveis
🚚 Sistema de Frete
✅ Cálculo baseado em distância e quantidade de pizzas
✅ Taxa base + taxa por km + taxa por pizza
✅ Recálculo automático ao alterar pedidos
📊 Análise de Dados
✅ Ranking de combinações de sabores mais populares
✅ Relatórios de vendas
✅ Estatísticas de pedidos
📁 Estrutura do Projeto
Unidade 04/
├── Projeto/
│   ├── Pizzaria.java          # Classe principal com menu
│   ├── Cliente.java           # Modelo de cliente
│   ├── Pedido.java           # Modelo de pedido
│   ├── Pizza.java            # Modelo de pizza
│   ├── Cardapio.java         # Gestão do cardápio e preços
│   ├── Frete.java            # Cálculo de frete
│   ├── AlterarPedido.java    # Operações de alteração
│   └── GrafoSabores.java     # Análise de combinações
├── dev.sh                    # Script de desenvolvimento
└── README.md
🚀 Como Executar
Pré-requisitos
Java 8 ou superior
Terminal/Prompt de comando
