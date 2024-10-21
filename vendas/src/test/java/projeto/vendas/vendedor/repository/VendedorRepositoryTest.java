package projeto.vendas.vendedor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projeto.vendas.vendedor.model.Vendedor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class VendedorRepositoryTest {

    @Autowired
    private VendedorRepository vendedorRepository;

    @BeforeEach
    void setup() {
        vendedorRepository.deleteAll();
    }

    @Test
    void deveSalvarEEncontrarVendedorPorId() {
        var vendedor = new Vendedor();
        vendedor.setNome("Adilson");
        vendedor.setCpf("12345678900");

        var vendedorSalvo = vendedorRepository.save(vendedor);
        var vendedorEncontrado = vendedorRepository.findById(vendedorSalvo.getId());

        assertTrue(vendedorEncontrado.isPresent());
        assertEquals(vendedorSalvo.getNome(), vendedorEncontrado.get().getNome());
    }

    @Test
    void optionalVazioPorIdInexistente() {

        var vendedorNaoEncontrado = vendedorRepository.findById(999L);

        assertTrue(vendedorNaoEncontrado.isEmpty());
    }
}
