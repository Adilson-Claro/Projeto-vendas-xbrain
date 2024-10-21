package projeto.vendas.produto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.produto.dto.ProdutoRequest;
import projeto.vendas.produto.dto.ProdutoResponse;
import projeto.vendas.produto.model.Produto;
import projeto.vendas.produto.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvarProduto(ProdutoRequest request) {

        var produto = construirProduto(request.nome(), request.valor());

        produtoRepository.save(produto);
        return produto;
    }

    private Produto construirProduto(String nome, BigDecimal valor) {
        return Produto.converter(null, nome, valor);
    }

    public List<ProdutoResponse> buscarProduto() {

        var produto = produtoRepository.findAll();

        if (produto.isEmpty()) {
            throw new NotFoundException("Produto não encontrado");
        }

        return produto.stream()
                .map(ProdutoResponse::converter)
                .toList();
    }

}
