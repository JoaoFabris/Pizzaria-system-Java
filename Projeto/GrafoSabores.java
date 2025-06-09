package Projeto;

import java.util.*;

public class GrafoSabores {
    private Map<String, Integer> combinacoes = new HashMap<>();

    public void adicionarPizza(Pizza pizza) {
        List<String> sabores = pizza.getSabores();
        if (sabores.size() > 1) {

            List<String> saboresOrdenados = new ArrayList<>(sabores);
            Collections.sort(saboresOrdenados);
            String combinacao = String.join(" + ", saboresOrdenados);

            combinacoes.put(combinacao, combinacoes.getOrDefault(combinacao, 0) + 1); // exemplo, (sabor1 + sabor2) -> 1
        }
    }

    public void processarPedidos(List<Pedido> listaPedidos) {
        combinacoes.clear(); // Limpar dados anteriores

        for (Pedido pedido : listaPedidos) {
            for (Pizza pizza : pedido.getPizzas()) {
                adicionarPizza(pizza);
            }
        }
    }

    public void exibirCombinacoes() { // USA O MAP DA CLASSE!
        System.out.println("Combinações de sabores:");
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(combinacoes.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int contador = 1;
        for (Map.Entry<String, Integer> entry : lista) {
            System.out.printf("%d. %s: %d%n", contador++, entry.getKey(), entry.getValue());
        }
    }

    public void limparCombinacoes() {
        combinacoes.clear();
        System.out.println("Combinações de sabores limpas.");
    }

}