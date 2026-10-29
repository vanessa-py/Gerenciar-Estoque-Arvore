package com.faculdade.arvore.tree;

import com.faculdade.arvore.model.ItemFormula;
import com.faculdade.arvore.service.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArvoreService {

    private final ProdutoService produtoService;

    public ArvoreService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public TreeNode buildTree() {
        List<ItemFormula> itens = produtoService.listarTodos();
        List<TreeNode> nos = new ArrayList<>();
        TreeNode raiz = null;

        for (ItemFormula item : itens) {
            nos.add(new TreeNode(item));
        }

        for (int i = 0; i < itens.size(); i++) {
            ItemFormula item = itens.get(i);

            if (item.getPai() == null) {
                raiz = nos.get(i);
            } else {
                for (int j = 0; j < itens.size(); j++) {
                    if (itens.get(j).getNome().equalsIgnoreCase(item.getPai())) {
                        nos.get(j).adicionarFilho(nos.get(i));
                        break;
                    }
                }
            }
        }

        return raiz;
    }

    public void printTree(TreeNode no, int nivel) {
        if (no == null) {
            return;
        }

        System.out.println("  ".repeat(nivel) + no.getItem().getNome());

        for (TreeNode filho : no.getFilhos()) {
            printTree(filho, nivel + 1);
        }
    }

    public Map<String, Integer> explodir(TreeNode raiz, int quantidadeProduzir) {
        Map<String, Integer> componentes = new LinkedHashMap<>();

        if (raiz == null) {
            return componentes;
        }

        for (TreeNode filho : raiz.getFilhos()) {
            explodirRecursivo(filho, quantidadeProduzir, componentes);
        }

        return componentes;
    }

    private void explodirRecursivo(
            TreeNode no,
            int quantidadePai,
            Map<String, Integer> componentes) {

        int quantidade = quantidadePai * no.getItem().getQuantidade();
        String nome = no.getItem().getNome();

        componentes.merge(nome, quantidade, Integer::sum);

        for (TreeNode filho : no.getFilhos()) {
            explodirRecursivo(filho, quantidade, componentes);
        }
    }

    public double calcularCusto(TreeNode raiz) {
        if (raiz == null) {
            return 0;
        }

        double total = 0;

        for (TreeNode filho : raiz.getFilhos()) {
            total += calcularCustoRecursivo(filho, 1);
        }

        return total;
    }

    private double calcularCustoRecursivo(TreeNode no, int quantidadePai) {
        int quantidade = quantidadePai * no.getItem().getQuantidade();
        double custo = quantidade * no.getItem().getPreco();

        for (TreeNode filho : no.getFilhos()) {
            custo += calcularCustoRecursivo(filho, quantidade);
        }

        return custo;
    }

    public double calcularValorAquisicao(TreeNode raiz, int quantidadeProduzir) {
        if (raiz == null) {
            return 0;
        }

        double total = 0;

        for (TreeNode filho : raiz.getFilhos()) {
            total += calcularCustoRecursivo(filho, quantidadeProduzir);
        }

        return total;
    }

    public double implodir(String nomeComponente, double novoPreco) {
        boolean atualizado = produtoService.atualizarPreco(nomeComponente, novoPreco);

        if (!atualizado) {
            throw new IllegalArgumentException("Componente não encontrado.");
        }

        TreeNode novaRaiz = buildTree();
        return calcularCusto(novaRaiz);
    }
}
