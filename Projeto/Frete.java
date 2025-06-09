package Projeto;

public class Frete {

    private static final double TAXA_BASE = 5.0; // Taxa fixa
    private static final double PRECO_POR_KM = 2.0; // R$ por km
    private static final double PRECO_POR_PIZZA = 1.5; // R$ por pizza

    public static double calcular(double distanciaKm, int quantidadePizzas) {
        double frete = TAXA_BASE + (distanciaKm * PRECO_POR_KM) + (quantidadePizzas * PRECO_POR_PIZZA);
        return Math.round(frete * 100.0) / 100.0;
    }

    public static void mostrarCalculo(double distanciaKm, int quantidadePizzas) {
        double taxaDistancia = distanciaKm * PRECO_POR_KM;
        double taxaPizzas = quantidadePizzas * PRECO_POR_PIZZA;
        double total = calcular(distanciaKm, quantidadePizzas);

        System.out.println("\n🚚 CÁLCULO DO FRETE:");
        System.out.println("━".repeat(30));
        System.out.printf("📦 Taxa base:           R$ %.2f\n", TAXA_BASE);
        System.out.printf("🛣️  Distância (%.1fkm):    R$ %.2f\n", distanciaKm, taxaDistancia);
        System.out.printf("🍕 Pizzas (%d unidades):  R$ %.2f\n", quantidadePizzas, taxaPizzas);
        System.out.println("━".repeat(30));
        System.out.printf(" FRETE TOTAL:         R$ %.2f\n", total); 
        System.out.println("━".repeat(30));
    }
}