package Projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Projeto.Pizza.TamanhoPizza;

public class Pizzaria {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        carregarDadosTeste(listaClientes, listaPedidos);

        boolean continuar = true;
        while (continuar) {
            System.out.println();
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido");
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas");
            System.out.println("5 - Gerar lista de clientes");
            System.out.println("6 - Mostrar pedidos");
            System.out.println("7 - Garfo de sabores");
            System.out.println("8 - Calcular frete");
            System.out.println("9 - Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner));
                    System.out.println("Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio();
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 6:
                    mostrarPedidos(listaPedidos);
                    break;
                case 7:
                    analisarCombinacoesSabores(scanner, listaPedidos);
                    break;
                case 8:
                    calcularFrete(scanner, listaClientes);
                    break;
                case 9:
                    System.out.println("Até amanha...");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    break;
            }
        }

    }

    private static void calcularFrete(Scanner scanner, List<Cliente> listaClientes) {
        System.out.println("🚚 CALCULADORA DE FRETE");
        
        if (listaClientes.isEmpty()) {
            System.out.println(" Nenhum cliente cadastrado!");
            return;
        }
        
        // Mostrar clientes
        System.out.println("\n👥 CLIENTES:");
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            System.out.printf("%d - %s (%.1fkm)\n", (i + 1), cliente.getNome(), cliente.getDistancia());
        }
        
        System.out.print("\nEscolha o cliente: ");
        int indice = scanner.nextInt() - 1;
        System.out.print("Quantas pizzas? ");
        int pizzas = scanner.nextInt();
        scanner.nextLine();
        
        if (indice >= 0 && indice < listaClientes.size() && pizzas > 0) {
            Cliente cliente = listaClientes.get(indice);
            double distancia = cliente.getDistancia();
            
            System.out.printf("\n Cliente: %s\n", cliente.getNome());
            System.out.printf(" Endereço: %s\n", cliente.getEndereco());
            
            Frete.mostrarCalculo(distancia, pizzas);
        } else {
            System.out.println("Dados inválidos!");
        }
    }

    private static void analisarCombinacoesSabores(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("ANALISAR COMBINAÇÕES DE SABORES");
        if (listaPedidos.isEmpty()) {
            System.out.println("Lista de pedidos está vazia");
            return;
        }

        GrafoSabores grafoSabores = new GrafoSabores();
        grafoSabores.processarPedidos(listaPedidos); // AQUI usa listaPedidos para PREENCHER o Map
        grafoSabores.exibirCombinacoes(); // AQUI passa listaPedidos mas NÃO USA!
    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.printf("%d - %s (%.1fkm)\n", x, cliente.getNome(), cliente.getDistancia());
            x++;
        }
        System.out.print("Opção: ");
        int cliente = scanner.nextInt();
        scanner.nextLine();

        boolean continuar = true;
        while (continuar) {
            x = 1;
            System.out.println("Qual o tamanho da pizza? ");
            System.out.println("Selecione um tamanho: ");
            for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                System.out.println(x + " - " + tamanhos);
                x++;
            }
            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("Digite a quantidade de sabores: 1 - 4 ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            Cardapio cardapio = new Cardapio();
            List<String> saboresList = new ArrayList<>();
            List<String> saboresSelect = new ArrayList<>();

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione um sabor: ");

                x = 1;
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x + " - " + sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcao - 1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect),
                    TamanhoPizza.getByIndex(tamanho - 1));
            pizzas.add(pizza);

            System.out.println("Pizza cadastrada com sucesso!");

            System.out.println();
            System.out.println("Deseja cadastrar mais uma pizza no pedido?");
            System.out.print("1 - Sim, 2 - Não: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao != 1) {
                continuar = false;
            }
        }

        double valorPizzas = somarPizzas(pizzas);
        double distancia = listaClientes.get(cliente - 1).getDistancia(); // ✅ Direto!
        int quantidadePizzas = pizzas.size();
        double frete = Frete.calcular(distancia, quantidadePizzas);

        Pedido pedido = new Pedido(
                listaPedidos.size() + 1,
                listaClientes.get(cliente - 1), // ✅ Direto da lista
                pizzas,
                valorPizzas,
                frete);
        listaPedidos.add(pedido);

    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        System.out.println("ALTERAR PEDIDO");
        if (listaPedidos.isEmpty()) {
            System.out.println("Lista de pedidos esta vazia");
        } else {
            System.out.println(" PEDIDOS DISPONÍVEIS:");
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido pedido = listaPedidos.get(i);
                System.out.println(
                        (i + 1) + " - " + pedido.getCliente().getNome() + " - Total: R$" + pedido.getValorTotal());
            }

        }
        System.out.print("Selecione o número do pedido que deseja alterar: ");
        int numeroPedido = scanner.nextInt() - 1;
        scanner.nextLine();
        if (numeroPedido < 0 || numeroPedido >= listaPedidos.size()) {
            System.out.println("Número de pedido inválido.");
            return;
        }
        Pedido pedido = listaPedidos.get(numeroPedido);

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n ALTERAR PEDIDO #" + pedido.getId());
            System.out.println("=".repeat(40));
            System.out.println("1 - Adicionar pizza");
            System.out.println("2 - Remover pizza");
            System.out.println("3 - Alterar sabores de uma pizza");
            System.out.println("4 - Ver pedido atual");
            System.out.println("9 - Finalizar alterações");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    AlterarPedido.adicionarPizzaPedido(scanner, pedido);
                    break;
                case 2:
                    AlterarPedido.removerPizzaPedido(scanner, pedido);
                    break;
                case 3:
                    AlterarPedido.alterarSaboresPizza(scanner, pedido);
                    break;
                case 4:
                    System.out.println("\n" + pedido);
                    break;
                case 8:
                    calcularFrete(scanner, listaClientes);
                    break;
                case 9:
                    System.out.println("Alterações finalizadas!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        System.out.println();
        System.out.print("Digite a distância do cliente (em km): ");
        double distanciaKm = scanner.nextDouble();
        System.out.println();
        Cliente cliente = new Cliente(nome, endereco, telefone, email, distanciaKm);
        return cliente;
    }

    private static void gerarRelatorio() {
        System.out.println("Gerar relatorio");
    }

    public static void mostrarPedidos(List<Pedido> listaPedidos) {
        if (listaPedidos.isEmpty()) {
            System.out.println("Lista de pedidos esta vazia");
        } else {
            for (Pedido pedido : listaPedidos) {
                System.out.println(pedido);
            }
        }
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        int x = 1;
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes esta vazia");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println("Cliente " + x);
                System.out.println(cliente.getNome());
                System.out.println(cliente.getEndereco());
                System.out.println(cliente.getTelefone());
                System.out.println(cliente.getEmail());
                System.out.println("Distância: " + cliente.getDistancia() + " km");
                System.out.println();
                x++;
            }
        }
    }

    /**
     * Carrega dados de teste automaticamente
     */
    private static void carregarDadosTeste(List<Cliente> listaClientes, List<Pedido> listaPedidos) {
        System.out.println("🔄 Carregando dados de teste...");

        // ===== CLIENTES DE TESTE =====
        listaClientes.add(new Cliente("João Silva", "Rua das Flores, 123", "(11) 99999-1111", "joao@email.com", 2.5));
        listaClientes.add(new Cliente("Maria Santos", "Av. Principal, 456", "(11) 99999-2222", "maria@email.com", 4.8));
        listaClientes.add(new Cliente("Pedro Costa", "Rua da Paz, 789", "(11) 99999-3333", "pedro@email.com", 1.2));
        listaClientes.add(new Cliente("Ana Oliveira", "Rua do Sol, 321", "(11) 99999-4444", "ana@email.com", 7.3));
        listaClientes.add(new Cliente("Carlos Lima", "Av. Central, 654", "(11) 99999-5555", "carlos@email.com", 12.1));

        // ===== CARDÁPIO PARA CRIAR PIZZAS =====
        Cardapio cardapio = new Cardapio();

        // ===== PEDIDOS DE TESTE =====

        // PEDIDO 1 - João Silva
        List<Pizza> pizzas1 = new ArrayList<>();
        pizzas1.add(new Pizza(List.of("Margherita", "Pepperoni"),
                cardapio.getPrecoJusto(List.of("Margherita", "Pepperoni")), TamanhoPizza.GRANDE));
        pizzas1.add(new Pizza(List.of("Calabresa"), cardapio.getPrecoJusto(List.of("Calabresa")), TamanhoPizza.BROTO));
        listaPedidos.add(new Pedido(1, listaClientes.get(0), pizzas1, somarPizzas(pizzas1),
                Frete.calcular(listaClientes.get(0).getDistancia(), pizzas1.size())));

        // PEDIDO 2 - Maria Santos
        List<Pizza> pizzas2 = new ArrayList<>();
        pizzas2.add(new Pizza(List.of("Portuguesa", "Calabresa"),
                cardapio.getPrecoJusto(List.of("Portuguesa", "Calabresa")), TamanhoPizza.GIGA));
        pizzas2.add(new Pizza(List.of("Quatro queijos", "Frango com Catupiry"),
                cardapio.getPrecoJusto(List.of("Quatro queijos", "Frango com Catupiry")), TamanhoPizza.GRANDE));
        listaPedidos.add(new Pedido(2, listaClientes.get(1), pizzas2, somarPizzas(pizzas2),
                Frete.calcular(listaClientes.get(1).getDistancia(), pizzas2.size())));

        // PEDIDO 3 - Pedro Costa
        List<Pizza> pizzas3 = new ArrayList<>();
        pizzas3.add(new Pizza(List.of("Margherita", "Pepperoni"),
                cardapio.getPrecoJusto(List.of("Margherita", "Pepperoni")), TamanhoPizza.GRANDE));
        pizzas3.add(new Pizza(List.of("Atum", "Mussarela"), cardapio.getPrecoJusto(List.of("Atum", "Mussarela")),
                TamanhoPizza.BROTO));
        listaPedidos.add(new Pedido(3, listaClientes.get(2), pizzas3, somarPizzas(pizzas3),
                Frete.calcular(listaClientes.get(2).getDistancia(), pizzas3.size())));

        // PEDIDO 4 - Ana Oliveira
        List<Pizza> pizzas4 = new ArrayList<>();
        pizzas4.add(new Pizza(List.of("Portuguesa", "Calabresa"),
                cardapio.getPrecoJusto(List.of("Portuguesa", "Calabresa")), TamanhoPizza.GIGA));
        pizzas4.add(
                new Pizza(List.of("Vegetariana"), cardapio.getPrecoJusto(List.of("Vegetariana")), TamanhoPizza.GRANDE));
        pizzas4.add(new Pizza(List.of("Especial da casa", "Pepperoni"),
                cardapio.getPrecoJusto(List.of("Especial da casa", "Pepperoni")), TamanhoPizza.BROTO));
        listaPedidos.add(new Pedido(4, listaClientes.get(3), pizzas4, somarPizzas(pizzas4),
                Frete.calcular(listaClientes.get(3).getDistancia(), pizzas4.size())));

        // PEDIDO 5 - Carlos Lima
        List<Pizza> pizzas5 = new ArrayList<>();
        pizzas5.add(new Pizza(List.of("Margherita", "Pepperoni", "Quatro queijos"),
                cardapio.getPrecoJusto(List.of("Margherita", "Pepperoni", "Quatro queijos")), TamanhoPizza.GIGA));
        pizzas5.add(new Pizza(List.of("Calabresa", "Portuguesa"),
                cardapio.getPrecoJusto(List.of("Calabresa", "Portuguesa")), TamanhoPizza.GRANDE));
        listaPedidos.add(new Pedido(5, listaClientes.get(4), pizzas5, somarPizzas(pizzas5),
                Frete.calcular(listaClientes.get(4).getDistancia(), pizzas5.size())));

        // PEDIDO 6 - João Silva (segundo pedido)
        List<Pizza> pizzas6 = new ArrayList<>();
        pizzas6.add(new Pizza(List.of("Frango com Catupiry", "Mussarela"),
                cardapio.getPrecoJusto(List.of("Frango com Catupiry", "Mussarela")), TamanhoPizza.GRANDE));
        listaPedidos.add(new Pedido(6, listaClientes.get(0), pizzas6, somarPizzas(pizzas6),
                Frete.calcular(listaClientes.get(0).getDistancia(), pizzas6.size())));

        System.out.println("✅ Dados carregados:");
        System.out.println("   👥 " + listaClientes.size() + " clientes");
        System.out.println("   📋 " + listaPedidos.size() + " pedidos");
        System.out.println("   🍕 " + listaPedidos.stream().mapToInt(p -> p.getPizzas().size()).sum() + " pizzas");
        System.out.println("   🚚 Frete calculado automaticamente");
        System.out.println();
    }
}