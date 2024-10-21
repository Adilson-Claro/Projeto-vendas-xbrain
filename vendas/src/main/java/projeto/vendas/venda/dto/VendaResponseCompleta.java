package projeto.vendas.venda.dto;

import projeto.vendas.produto.dto.ProdutoResponse;
import projeto.vendas.vendedor.dto.VendedorResponse;

public record VendaResponseCompleta(VendaResponse venda,
                                    ProdutoResponse produto,
                                    VendedorResponse vendedor) {

    public static VendaResponseCompleta converter(VendaResponse venda, ProdutoResponse produto, VendedorResponse vendedor) {
        return new VendaResponseCompleta(venda,
                produto,
                vendedor);
    }
}
