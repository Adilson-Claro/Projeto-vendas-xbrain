package projeto.vendas.produto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.produto.dto.ProdutoRequest;
import projeto.vendas.produto.dto.ProdutoResponse;
import projeto.vendas.produto.model.Produto;
import projeto.vendas.produto.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarProduto() {
        var request = new ProdutoRequest("Mouse", BigDecimal.valueOf(10.0));
        var produtoMock = new Produto(null, "Mouse", BigDecimal.valueOf(10.0));

        when(produtoRepository.save(any(Produto.class))).thenReturn(new Produto(1L, "Mouse", BigDecimal.valueOf(10.0)));

        var resultado = produtoService.salvarProduto(request);

        assertEquals("Mouse", resultado.getNome());
        assertEquals(BigDecimal.valueOf(10.0), resultado.getValor());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveRetornarListaDeProdutos() {
        var produtoMock = new Produto(1L, "Cadeira", BigDecimal.valueOf(10.0));
        var produtos = Collections.singletonList(produtoMock);

        when(produtoRepository.findAll()).thenReturn(produtos);

        var resultado = produtoService.buscarProduto();

        assertEquals(1, resultado.size());
        assertEquals(ProdutoResponse.converter(produtoMock), resultado.get(0));
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveLancarNotFoundExceptionQuandoNaoExistiremProdutos() {
        when(produtoRepository.findAll()).thenReturn(Collections.emptyList());

        var exception = assertThrows(NotFoundException.class, () -> {
            produtoService.buscarProduto();
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, times(1)).findAll();
    }
}
