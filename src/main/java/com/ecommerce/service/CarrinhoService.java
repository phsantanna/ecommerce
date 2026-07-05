package com.ecommerce.service;

import com.ecommerce.controller.dto.CarrinhoDtoRequest;
import com.ecommerce.controller.dto.CarrinhoDtoResponse;
import com.ecommerce.controller.dto.UsuarioDtoRequest;
import com.ecommerce.model.Carrinho;
import com.ecommerce.model.Produto;
import com.ecommerce.model.ProdutoCarrinho;
import com.ecommerce.model.Usuario;
import com.ecommerce.model.mappers.CarrinhoMapper;
import com.ecommerce.enums.CarrinhoErroTipo;
import com.ecommerce.exceptions.CarrinhoException;
import com.ecommerce.repository.CarrinhoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoMapper carrinhoMapper;
    private final ProdutoService produtoService;
    private final ProdutoCarrinhoService produtoCarrinhoService;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, CarrinhoMapper carrinhoMapper,
                           ProdutoService produtoService, ProdutoCarrinhoService produtoCarrinhoService) {
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoMapper = carrinhoMapper;
        this.produtoService = produtoService;
        this.produtoCarrinhoService = produtoCarrinhoService;
    }

    @Transactional
    public Carrinho criarCarrinho(Usuario usuario) {
        if (carrinhoRepository.existsByUsuarioId(usuario.getId())) {
            throw new CarrinhoException(CarrinhoErroTipo.CARRINHO_JA_EXISTENTE);
        }

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setValorTotal(BigDecimal.ZERO);

        return carrinhoRepository.save(carrinho);
    }

    @Transactional(readOnly = true)
    public CarrinhoDtoResponse obterCarrinhoPorUsuario(UsuarioDtoRequest usuario) {
        return carrinhoRepository.findCarrinhoByUsuarioId(usuario.id())
                .map(carrinhoMapper::toDto)
                .orElseThrow(() -> new CarrinhoException(CarrinhoErroTipo.USUARIO_NAO_ENCONTRADO));
    }

    @Transactional
    public CarrinhoDtoResponse inserirNoCarrinho(CarrinhoDtoRequest carrinhoDtoRequest) {
        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuarioId(carrinhoDtoRequest.idUsuario())
                .orElseThrow(() -> new CarrinhoException(CarrinhoErroTipo.CARRINHO_NAO_ENCONTRADO));

        Produto produto = produtoService.buscarProdutoPorId(carrinhoDtoRequest.idProduto());

        ProdutoCarrinho produtoCarrinho = produtoCarrinhoService.adicionarProdutoCarrinho(produto, carrinho);

        carrinho.getProdutosCarrinho().removeIf(item -> item.getProduto().getId().equals(produto.getId()));
        carrinho.getProdutosCarrinho().add(produtoCarrinho);

        BigDecimal novoValorTotal = carrinho.getProdutosCarrinho().stream()
                .map(item -> {
                    BigDecimal preco = item.getProduto().getPrecoProduto();
                    BigDecimal qtd = BigDecimal.valueOf(item.getQuantidade());
                    return preco.multiply(qtd);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carrinho.setValorTotal(novoValorTotal);

        carrinhoRepository.save(carrinho);

        return carrinhoMapper.toDto(carrinho);
    }
}