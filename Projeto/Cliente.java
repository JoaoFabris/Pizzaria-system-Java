package Projeto;

public class Cliente {
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private double distanciaKm;

    public Cliente(String nome, String endereco, String telefone, String email, double distanciaKm) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.distanciaKm = distanciaKm; // Padrão
    }

    public String getNome(){
        return nome;
    }
    public String getEndereco(){
        return endereco;
    }
    public String getTelefone(){
        return telefone;
    }
    public String getEmail(){
        return email;
    }

    public double getDistancia() {
        return distanciaKm;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void getDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}