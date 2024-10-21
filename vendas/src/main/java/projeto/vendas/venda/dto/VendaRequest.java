package projeto.vendas.venda.dto;

import jakarta.validation.constraints.NotNull;

public record VendaRequest(@NotNull Long produtoId,
                           @NotNull Long vendedorId,
                           @NotNull Integer quantidade) {
}
