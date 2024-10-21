package projeto.vendas.venda.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.vendas.venda.dto.VendaRequest;
import projeto.vendas.venda.dto.VendaResponseCompleta;
import projeto.vendas.venda.dto.VendaResponseMedia;
import projeto.vendas.venda.service.VendaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("venda")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaRequest> salvarVenda(@RequestBody @Valid VendaRequest request) {
        vendaService.salvarVenda(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseCompleta>> buscarVenda() {
        var vendas = vendaService.buscarVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("media")
    public ResponseEntity<VendaResponseMedia> calculaMediaVenda(
            @RequestBody @RequestParam("dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestBody @RequestParam("dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {

        var response = vendaService.calcularMediaVenda(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }
}
