package projeto.vendas.vendedor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.vendedor.dto.VendedorRequest;
import projeto.vendas.vendedor.model.Vendedor;
import projeto.vendas.vendedor.repository.VendedorRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendedorServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @InjectMocks
    private VendedorService vendedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarVendedor() {
        var request = new VendedorRequest("João", "12345678900");
        var vendedor = Vendedor.converter(null, request.nome(), request.cpf());

        when(vendedorRepository.save(any(Vendedor.class))).thenReturn(vendedor);

        var vendedorSalvo = vendedorService.salvarVendedor(request);

        assertNotNull(vendedorSalvo);
        assertEquals(request.nome(), vendedorSalvo.getNome());
        assertEquals(request.cpf(), vendedorSalvo.getCpf());
        verify(vendedorRepository, times(1)).save(any(Vendedor.class));
    }

    @Test
    void deveRetornarListaDeVendedores() {
        Vendedor vendedor = Vendedor.converter(null, "João", "12345678900");
        when(vendedorRepository.findAll()).thenReturn(List.of(vendedor));

        var vendedores = vendedorService.buscarVendedor();

        assertFalse(vendedores.isEmpty());
        assertEquals(1, vendedores.size());
        verify(vendedorRepository, times(1)).findAll();
    }

    @Test
    void deveLancarNotFoundExceptionQuandoListaVazia() {
        when(vendedorRepository.findAll()).thenReturn(Collections.emptyList());

        var exception = assertThrows(NotFoundException.class, () -> vendedorService.buscarVendedor());
        assertEquals("Vendedor não encontrado", exception.getMessage());
        verify(vendedorRepository, times(1)).findAll();
    }
}
