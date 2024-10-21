package projeto.vendas.vendedor.dto;

import projeto.vendas.vendedor.model.Vendedor;

public record VendedorResponse(Long id,
                               String nome,
                               String cpf) {

    public static VendedorResponse converter(Vendedor vendedor) {
        return new VendedorResponse(vendedor.getId(),
                vendedor.getNome(),
                vendedor.getCpf());
    }
}
