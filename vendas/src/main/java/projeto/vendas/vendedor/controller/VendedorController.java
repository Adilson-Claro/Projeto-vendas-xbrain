package projeto.vendas.vendedor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.vendas.vendedor.dto.VendedorRequest;
import projeto.vendas.vendedor.dto.VendedorResponse;
import projeto.vendas.vendedor.service.VendedorService;

import java.util.List;

@RestController
@RequestMapping("vendedor")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorService vendedorService;

    @PostMapping
    public ResponseEntity<VendedorResponse> salvarVendedor(@RequestBody @Valid VendedorRequest request) {
        var vendedorCriado = VendedorResponse.converter(vendedorService.salvarVendedor(request));
        return ResponseEntity.ok(vendedorCriado);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponse>> buscarVendedores() {
        var vendedores = vendedorService.buscarVendedor();
        return ResponseEntity.ok(vendedores);
    }
}
