package projeto.vendas.produto.dto;

import projeto.vendas.produto.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id,
                              String nome,
                              BigDecimal valor) {

    public static ProdutoResponse converter(Produto produto) {
        return new ProdutoResponse(produto.getId(),
                produto.getNome(),
                produto.getValor());
    }
}
