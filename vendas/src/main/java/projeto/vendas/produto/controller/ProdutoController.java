package projeto.vendas.produto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.vendas.produto.dto.ProdutoRequest;
import projeto.vendas.produto.dto.ProdutoResponse;
import projeto.vendas.produto.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvarProduto(@RequestBody @Valid ProdutoRequest request) {
        var produtoCriado = ProdutoResponse.converter(produtoService.salvarProduto(request));
        return ResponseEntity.ok(produtoCriado);
    }

    @RequestMapping
    public ResponseEntity<List<ProdutoResponse>> buscarProduto() {
        var produtos = produtoService.buscarProduto();
        return ResponseEntity.ok(produtos);
    }
}
