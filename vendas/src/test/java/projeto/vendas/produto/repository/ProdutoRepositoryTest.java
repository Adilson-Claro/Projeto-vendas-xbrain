package projeto.vendas.produto.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projeto.vendas.produto.model.Produto;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarProdutoQuandoExistir() {
        var id = 1L;
        var produto = new Produto(id, "Teclado", BigDecimal.valueOf(10.0));

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        var resultado = produtoRepository.findById(id);

        assertEquals(produto, resultado.orElse(null));
        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarNotFoundExceptionQuandoNaoExistirProduto() {
        var id = 2L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        var resultado = produtoRepository.findById(id);

        assertEquals(Optional.empty(), resultado);
        verify(produtoRepository, times(1)).findById(id);
    }
}
