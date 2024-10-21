package projeto.vendas.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.vendas.venda.model.Venda;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDataCadastroBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}
