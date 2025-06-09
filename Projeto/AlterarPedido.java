package Projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Projeto.Pizza.TamanhoPizza;

public class AlterarPedido {
    /**
     * ADICIONAR PIZZA AO PEDIDO
     */

    public static void adicionarPizzaPedido(Scanner scanner, Pedido pedido) {
        System.out.println("Adicionar Pizza ao pedido!");
        System.out.println("Cliente: " + pedido.getCliente().getNome());

        int x = 1;
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

            for (String sabor : cardapio.getCardapio().keySet()) {
                saboresList.add(sabor);
            }

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione um sabor: ");

                x = 1;
                for (String sabor : saboresList) {
                    System.out.println(x + " - " + sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao >= 1 && opcao <= saboresList.size()) {
                    saboresSelect.add(saboresList.get(opcao - 1));
                } else {
                    System.out.println("Opção inválida!");
                    i--;
                }
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect),
                    TamanhoPizza.getByIndex(tamanho - 1));

            try {
                pedido.adicionarPizza(pizza);
                System.out.println(" Pizza adicionada ao pedido!");
            } catch (Exception e) {
                System.out.println(" Erro ao adicionar pizza: " + e.getMessage());
            }

            System.out.println("Pizza cadastrada com sucesso!");

            // MOSTRAR INFORMAÇÕES DA PIZZA ADICIONADA
            System.out.printf("Pizza adicionada: \n",
                    pizza.getTamanho(),
                    String.join(" + ", pizza.getSabores()),
                    pizza.getPreco());
            System.out.printf(" Novo total: ", pedido.getValorTotal());

            System.out.println("Deseja adicionar mais uma pizza?");
            System.out.print("1 - Sim, 2 - Não: ");
            int opcaoContinuar = scanner.nextInt();
            scanner.nextLine();

            if (opcaoContinuar != 1) {
                continuar = false;
            }
        }

        System.out.println("Pizzas adicionadas com sucesso!!");
        System.out.println("PEDIDO ATUALIZADO:");
        System.out.println("=".repeat(50));
        System.out.println(pedido);
    }

    // REMOVER PIZZA DO PEDIDO
    public static void removerPizzaPedido(Scanner scanner, Pedido pedido) {
        System.out.println("REMOVER PIZZA");

        if (pedido.getPizzas().isEmpty()) {
            System.out.println("Sem pizzas!");
            return;
        }

        // Mostrar pizzas
        List<Pizza> pizzas = pedido.getPizzas();
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza p = pizzas.get(i);
            System.out.printf("%d. %s - %s - R$ %.2f\n",
                    (i + 1), p.getTamanho(), String.join(" + ", p.getSabores()), p.getPreco());
        }

        // Escolher e remover
        System.out.print("\nQual pizza remover? ");
        int num = scanner.nextInt() - 1;
        scanner.nextLine();

        if (num >= 0 && num < pizzas.size()) {
            Pizza removida = pizzas.get(num);
            pedido.removerPizza(removida);

            // Recalcular frete
            pedido.setFrete(Frete.calcular(pedido.getCliente().getDistancia(), pedido.getPizzas().size()));

            System.out.printf("Removida: %s\n", String.join(" + ", removida.getSabores()), removida.getTamanho(), removida.getPreco());
            System.out.printf(" Novo total: R$ %.2f\n", pedido.getTotalComFrete());
            for (int i = 0; i < pizzas.size(); i++) {
                Pizza p = pizzas.get(i);
                System.out.printf("%d. %s - %s - R$ %.2f\n",
                        (i + 1), p.getTamanho(), String.join(" + ", p.getSabores()), p.getPreco());
            }
        } else {
            System.out.println("Número inválido!");
        }
    }

    /**
     * ALTERAR DADOS DO PEDIDO
     */
    public static void alterarSaboresPizza(Scanner scanner, Pedido pedido) {
        if (pedido.getPizzas().isEmpty()) {
            System.out.println("Sem pizzas!");
            return;
        }

        // Mostrar pizzas
        for (int i = 0; i < pedido.getPizzas().size(); i++) {
            Pizza p = pedido.getPizzas().get(i);
            System.out.printf("%d. %s - %s\n", (i + 1), p.getTamanho(), String.join(" + ", p.getSabores()));
        }

        System.out.print("Qual pizza? ");
        int num = scanner.nextInt() - 1;
        System.out.print("Quantos sabores? ");
        int qtd = scanner.nextInt();
        scanner.nextLine();

        if (num < 0 || num >= pedido.getPizzas().size() || qtd < 1 || qtd > 4) {
            System.out.println("Inválido!");
            return;
        }

        Pizza pizza = pedido.getPizzas().get(num);
        Cardapio cardapio = new Cardapio();
        List<String> sabores = new ArrayList<>(cardapio.getCardapio().keySet());
        List<String> novos = new ArrayList<>();

        // Escolher sabores
        for (int i = 0; i < qtd; i++) {
            System.out.printf("Sabor \n", (i + 1));
            for (int j = 0; j < sabores.size(); j++) {
                System.out.printf("%d-%s ", (j + 1), sabores.get(j));
            }
            System.out.print("\nOpção: ");
            int op = scanner.nextInt() - 1;
            if (op >= 0 && op < sabores.size() && !novos.contains(sabores.get(op))) {
                novos.add(sabores.get(op));
            } else {
                i--;
            }
        }

        // Atualizar
        double precoAntigo = pizza.getPreco();
        double novoPreco = cardapio.getPrecoJusto(novos);

        pizza.setSabores(novos);
        pizza.setPreco(novoPreco);
        pedido.setValorTotal(pedido.getValorTotal() - precoAntigo + novoPreco);

        System.out.printf(" Alterado! Novo: %s - R$ %.2f\n", String.join(" + ", novos), novoPreco);
        scanner.nextLine();
    }
}