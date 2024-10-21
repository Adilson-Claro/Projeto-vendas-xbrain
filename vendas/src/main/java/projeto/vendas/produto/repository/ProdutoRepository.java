package projeto.vendas.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.vendas.produto.model.Produto;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findById(Long id);
}
