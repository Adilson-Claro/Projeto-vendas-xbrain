package projeto.vendas.venda.dto;

import projeto.vendas.venda.model.Venda;

import java.math.BigDecimal;

public record VendaResponse(Long id,
                            Integer quantidade,
                            BigDecimal totalVendas) {

    public static VendaResponse converter(Venda venda) {
        return new VendaResponse(venda.getId(),
                venda.getQuantidade(),
                venda.getTotalVenda());
    }
}
