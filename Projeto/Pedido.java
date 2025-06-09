package Projeto;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;
    private double frete;

    // Construtor
    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal, double frete) {
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
        this.frete = frete;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getFrete() {
        return frete;
    }

    public double getTotalComFrete() {
        return valorTotal + frete;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

      public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
        // Recalcular valor total quando pizzas mudarem
        this.valorTotal = calcularValorTotal();
    }


    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Métodos utilitários
    public void adicionarPizza(Pizza pizza) {
        this.pizzas.add(pizza);
        this.valorTotal += pizza.getPreco();
    }

    public void removerPizza(Pizza pizza) {
        if (this.pizzas.remove(pizza)) {
            this.valorTotal -= pizza.getPreco();
        }
    }

    public int getQuantidadePizzas() {
        return pizzas.size();
    }

    private double calcularValorTotal() {
        return pizzas.stream()
                .mapToDouble(Pizza::getPreco)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // ess objeto ajuda a concatenar strings com +
        sb.append("=".repeat(40)).append("\n");
        sb.append(String.format("📋 PEDIDO #%d\n", id));
        sb.append("=".repeat(40)).append("\n");
        sb.append(String.format("👤 Cliente: %s\n", cliente.getNome()));
        sb.append(String.format("📞 Telefone: %s\n", cliente.getTelefone()));
        sb.append(String.format("📍 Endereço: %s\n", cliente.getEndereco()));
        sb.append("\n🍕 PIZZAS:\n");

        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);

            sb.append("  " + (i + 1) + ". " + pizza.getTamanho() +
                    " - " + String.join(" + ", pizza.getSabores()) +
                    " - R$ " + pizza.getPreco() + "\n");
        }

        sb.append("💰 Subtotal: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
        sb.append("🚚 Frete: R$ ").append(String.format("%.2f", frete)).append("\n");
        sb.append("💳 TOTAL: R$ ").append(String.format("%.2f", getTotalComFrete()));

        return sb.toString();
    }
}