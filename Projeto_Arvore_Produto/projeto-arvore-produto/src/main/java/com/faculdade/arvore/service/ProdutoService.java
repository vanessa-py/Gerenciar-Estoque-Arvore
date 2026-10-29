package com.faculdade.arvore.service;

import com.faculdade.arvore.model.ItemFormula;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private List<ItemFormula> itens;

    public ProdutoService() {
        this.itens = new ArrayList<>();
    }

    public ProdutoService(List<ItemFormula> itensIniciais) {
        this.itens = new ArrayList<>(itensIniciais);
    }

    public void cadastrarItem(String nome, String pai, int quantidade, double preco) {
        ItemFormula novoItem = new ItemFormula(nome, pai, quantidade, preco);
        itens.add(novoItem);
    }

    public List<ItemFormula> listarTodos() {
        return new ArrayList<>(itens);
    }

    public Optional<ItemFormula> buscarPorNome(String nome) {
        return itens.stream()
                .filter(item -> item.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public boolean atualizarPreco(String nome, double novoPreco) {
        Optional<ItemFormula> item = buscarPorNome(nome);

        if (item.isPresent()) {
            item.get().setPreco(novoPreco);
            return true;
        }

        return false;
    }

    public boolean atualizarQuantidade(String nome, int novaQuantidade) {
        Optional<ItemFormula> item = buscarPorNome(nome);

        if (item.isPresent()) {
            item.get().setQuantidade(novaQuantidade);
            return true;
        }

        return false;
    }

    public boolean deletarItem(String nome) {
        Optional<ItemFormula> item = buscarPorNome(nome);

        if (item.isEmpty()) {
            return false;
        }

        itens.remove(item.get());
        return true;
    }
}
