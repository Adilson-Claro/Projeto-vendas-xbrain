package projeto.vendas.vendedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.vendas.vendedor.model.Vendedor;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findById(Long id);
}
