package projeto.vendas.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequest(@NotBlank String nome,
                             @NotNull BigDecimal valor) {
}
