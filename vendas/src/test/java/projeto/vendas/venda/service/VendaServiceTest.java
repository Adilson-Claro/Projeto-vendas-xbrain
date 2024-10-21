package projeto.vendas.venda.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.produto.model.Produto;
import projeto.vendas.produto.repository.ProdutoRepository;
import projeto.vendas.venda.dto.VendaRequest;
import projeto.vendas.venda.model.Venda;
import projeto.vendas.venda.repository.VendaRepository;
import projeto.vendas.vendedor.model.Vendedor;
import projeto.vendas.vendedor.repository.VendedorRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarVenda() {
        var vendaRequest = new VendaRequest(1L, 1L, 1);
        var produto = new Produto(1L, "Notebook", BigDecimal.valueOf(2000.0));
        var vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Adilson");
        vendedor.setCpf("12312312344");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendaRepository.save(any(Venda.class))).thenReturn(new Venda());

        vendaService.salvarVenda(vendaRequest);

        verify(produtoRepository, times(1)).findById(1L);
        verify(vendedorRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).save(any(Venda.class));
    }

    @Test
    void deveLancarNotFoundExceptionParaProdutoInexistente() {
        var vendaRequest = new VendaRequest(1L, 1L, 1);
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> {
            vendaService.salvarVenda(vendaRequest);
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void deveLancarNotFoundExceptionParaVendedorInexistente() {
        var vendaRequest = new VendaRequest(1L, 1L, 1);
        var produto = new Produto(1L, "Produto Exemplo", BigDecimal.valueOf(10.0));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> {
            vendaService.salvarVenda(vendaRequest);
        });

        assertEquals("Vendedor não encontrado.", exception.getMessage());
    }

    @Test
    void deveCalcularMediaVenda() {
        var dataInicio = LocalDate.of(2023, 1, 1);
        var dataFim = LocalDate.of(2023, 1, 10);
        var venda = Venda.converter(1L, 1L, 1, BigDecimal.valueOf(100.0));

        when(vendaRepository.findByDataCadastroBetween(any(), any())).thenReturn(List.of(venda));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(new Vendedor(1L, "Vendedor Exemplo", "12312312366")));

        var media = vendaService.calcularMediaVenda(dataInicio, dataFim);

        assertNotNull(media);
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.CEILING), media.getMediaVendasDiaria());
        assertEquals(1, media.getTotalVendas());
        assertEquals("Vendedor Exemplo", media.getVendedorNome());
    }

    @Test
    void deveLancarExceptionParaDataInicioPosteriorAFim() {
        var dataInicio = LocalDate.of(2023, 1, 10);
        var dataFim = LocalDate.of(2023, 1, 1);

        var exception = assertThrows(ResponseStatusException.class, () -> {
            vendaService.calcularMediaVenda(dataInicio, dataFim);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("A data de início não pode ser após a data de fim.", exception.getReason());
    }
}
