package projeto.vendas.venda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projeto.vendas.common.utils.NotFoundException;
import projeto.vendas.produto.dto.ProdutoResponse;
import projeto.vendas.produto.model.Produto;
import projeto.vendas.produto.repository.ProdutoRepository;
import projeto.vendas.venda.dto.VendaRequest;
import projeto.vendas.venda.dto.VendaResponse;
import projeto.vendas.venda.dto.VendaResponseCompleta;
import projeto.vendas.venda.dto.VendaResponseMedia;
import projeto.vendas.venda.model.Venda;
import projeto.vendas.venda.repository.VendaRepository;
import projeto.vendas.vendedor.dto.VendedorResponse;
import projeto.vendas.vendedor.model.Vendedor;
import projeto.vendas.vendedor.repository.VendedorRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendedorRepository vendedorRepository;
    private final ProdutoRepository produtoRepository;

    public void salvarVenda(VendaRequest request) {
        var produto = verificarProdutoExistente(request.produtoId());

        var vendedor = verificarVendedorExistente(request.vendedorId());

        var totalVenda = calcularTotalVenda(produto.getValor(), request.quantidade());

        var venda = construirVenda(vendedor.getId(), produto.getId(), request.quantidade(), totalVenda);

        vendaRepository.save(venda);

    }

    private Produto verificarProdutoExistente(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }

    private Vendedor verificarVendedorExistente(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendedor não encontrado."));
    }

    private BigDecimal calcularTotalVenda(BigDecimal valorProduto, int quantidade) {
        return BigDecimal.valueOf(quantidade).multiply(valorProduto);
    }

    private Venda construirVenda(Long vendedorId, Long produtoId, Integer quantidade, BigDecimal totalVenda) {
        return Venda.converter(vendedorId, produtoId, quantidade, totalVenda);
    }

    public List<VendaResponseCompleta> buscarVendas() {
        var vendas = vendaRepository.findAll();

        return vendas
                .stream()
                .map(venda -> {
                    var vendedor = VendedorResponse.converter(vendedorRepository.findById(venda.getVendedorId())
                            .orElseThrow(() -> new NotFoundException("Vendedor não encontrado.")));
                    var produto = ProdutoResponse.converter(produtoRepository.findById(venda.getProdutoId())
                            .orElseThrow(() -> new NotFoundException("Produto não encontrado.")));

                    return VendaResponseCompleta.converter(VendaResponse.converter(venda),
                            produto,
                            vendedor);
                }).toList();
    }

    public VendaResponseMedia calcularMediaVenda(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de início não pode ser após a data de fim.");
        }

        var vendas = vendaRepository.findByDataCadastroBetween(dataInicio.atStartOfDay(), dataFim.atTime(23, 59, 59));

        var totalVendas = vendas.size();
        var totalValorVendas = vendas.stream()
                .map(Venda::getTotalVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var dias = (int) ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;

        var mediaVendasPorDia = dias > 0 ? totalValorVendas.divide(BigDecimal.valueOf(dias), 2, RoundingMode.CEILING) : BigDecimal.ZERO;

        var vendedorId = vendas.stream()
                .map(Venda::getVendedorId)
                .findFirst()
                .orElse(null);

        var vendedorNome = vendedorId != null ? vendedorRepository.findById(vendedorId)
                .map(Vendedor::getNome)
                .orElse(null) : null;

        return new VendaResponseMedia(dataInicio, dataFim, mediaVendasPorDia, vendedorId, vendedorNome, totalVendas);
    }
}
