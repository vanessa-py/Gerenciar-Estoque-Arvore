package com.faculdade.arvore.model;

public class ItemFormula {

    private String nome;
    private String pai;
    private int quantidade;
    private double preco;

    public ItemFormula() {
    }

    public ItemFormula(String nome, String pai, int quantidade, double preco) {
        this.nome = nome;
        this.pai = pai;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format(
            "Item: %s | Pai: %s | Quantidade: %d | Preço: R$ %.2f",
            nome,
            pai == null ? "Nenhum (Raiz)" : pai,
            quantidade,
            preco
        );
    }
}
