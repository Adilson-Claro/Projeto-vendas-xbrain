package projeto.vendas.vendedor.dto;

import jakarta.validation.constraints.NotBlank;

public record VendedorRequest(@NotBlank String nome,
                              @NotBlank String cpf) {
}
