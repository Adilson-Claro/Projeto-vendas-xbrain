package projeto.vendas.vendedor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.vendedor.dto.VendedorRequest;
import projeto.vendas.vendedor.dto.VendedorResponse;
import projeto.vendas.vendedor.model.Vendedor;
import projeto.vendas.vendedor.repository.VendedorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    public Vendedor salvarVendedor(VendedorRequest request) {

        var vendedor = construirVendedor(request.nome(), request.cpf());

        vendedorRepository.save(vendedor);
        return vendedor;
    }

    private Vendedor construirVendedor(String nome, String cpf) {

        return Vendedor.converter(null, nome, cpf);
    }

    public List<VendedorResponse> buscarVendedor() {
        var vendedor = vendedorRepository.findAll();

        if (vendedor.isEmpty()) {
            throw new NotFoundException("Vendedor não encontrado");
        }

        return vendedor.stream()
                .map(VendedorResponse::converter)
                .toList();
    }
}
