package projeto.vendas.venda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long vendedorId;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal totalVenda;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    public static Venda converter(Long vendedorId, Long produtoId, Integer quantidade, BigDecimal totalVenda) {
        var venda = new Venda();
        venda.setVendedorId(vendedorId);
        venda.setProdutoId(produtoId);
        venda.setQuantidade(quantidade);
        venda.setTotalVenda(totalVenda);
        venda.setDataCadastro(LocalDateTime.now());
        return venda;
    }
}