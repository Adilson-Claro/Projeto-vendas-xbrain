package projeto.vendas.venda.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projeto.vendas.venda.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class VendaRepositoryTest {

    @Mock
    private VendaRepository vendaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarVendasDentroDoIntervalo() {
        var dataInicio = LocalDateTime.of(2024, 10, 1, 0, 0);
        var dataFim = LocalDateTime.of(2024, 10, 31, 23, 59);
        var venda1 = new Venda(1L, 1L, 1L, 2, BigDecimal.valueOf(20.0), dataInicio);
        var venda2 = new Venda(2L, 2L, 2L, 1, BigDecimal.valueOf(10.0), dataFim);
        List<Venda> vendasMock = new ArrayList<>();
        vendasMock.add(venda1);
        vendasMock.add(venda2);

        when(vendaRepository.findByDataCadastroBetween(dataInicio, dataFim)).thenReturn(vendasMock);

        var resultado = vendaRepository.findByDataCadastroBetween(dataInicio, dataFim);

        assertEquals(2, resultado.size());
        assertEquals(venda1, resultado.get(0));
        assertEquals(venda2, resultado.get(1));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremVendasNoIntervalo() {
        var dataInicio = LocalDateTime.of(2024, 10, 1, 0, 0);
        var dataFim = LocalDateTime.of(2024, 10, 31, 23, 59);

        when(vendaRepository.findByDataCadastroBetween(dataInicio, dataFim)).thenReturn(new ArrayList<>());

        var resultado = vendaRepository.findByDataCadastroBetween(dataInicio, dataFim);

        assertEquals(0, resultado.size());
    }
}
