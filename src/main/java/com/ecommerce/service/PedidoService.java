package com.ecommerce.service;

import com.ecommerce.controller.dto.PedidoRequestDto;
import com.ecommerce.controller.dto.PedidoResponseDto;
import com.ecommerce.entity.*;
import com.ecommerce.entity.mappers.PedidoMapper;
import com.ecommerce.enums.CarrinhoErroTipo;
import com.ecommerce.enums.StatusPedido;
import com.ecommerce.enums.UsuarioErroTipo;
import com.ecommerce.exceptions.CarrinhoException;
import com.ecommerce.exceptions.UsuarioException;
import com.ecommerce.repository.CarrinhoRepository;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProdutoCarrinhoRepository;
import com.ecommerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final UsuarioRepository usuarioRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoCarrinhoRepository produtoCarrinhoRepository;

    public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
                         UsuarioRepository usuarioRepository, CarrinhoRepository carrinhoRepository,
                         ProdutoCarrinhoRepository produtoCarrinhoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.usuarioRepository = usuarioRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.produtoCarrinhoRepository = produtoCarrinhoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listarPedidosUsuario(UUID idUsuario) {
        var pedidos = pedidoRepository.findAllByUsuarioId(idUsuario).orElseThrow(() -> new UsuarioException(UsuarioErroTipo.USUARIO_NAO_ENCONTRADO));
        return pedidoMapper.toResponseDtoList(pedidos);
    }

    @Transactional
    public PedidoResponseDto finalizarPedido(PedidoRequestDto pedidoRequestDto) {

        Usuario usuario = usuarioRepository.findUsuarioById(pedidoRequestDto.idUsuario()).orElseThrow(() -> new UsuarioException(UsuarioErroTipo.USUARIO_NAO_ENCONTRADO));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuarioId(usuario.getId())
                .orElseThrow(() -> new CarrinhoException(CarrinhoErroTipo.CARRINHO_NAO_ENCONTRADO));

        if (carrinho.getProdutosCarrinho().isEmpty()) {
            throw new CarrinhoException(CarrinhoErroTipo.CARRINHO_VAZIO);
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setValorTotal(carrinho.getValorTotal());


        List<ItemPedido> itensDoPedido = new ArrayList<>();
        for (ProdutoCarrinho pc : carrinho.getProdutosCarrinho()) {
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(pc.getProduto());
            item.setQuantidade(pc.getQuantidade());
            item.setPrecoUnitario(pc.getProduto().getPrecoProduto());

            itensDoPedido.add(item);
        }

        pedido.setItens(itensDoPedido);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);


        List<ProdutoCarrinho> itensParaDeletar = new ArrayList<>(carrinho.getProdutosCarrinho());

        carrinho.getProdutosCarrinho().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinhoRepository.save(carrinho);

        produtoCarrinhoRepository.deleteAll(itensParaDeletar);

        return pedidoMapper.toResponseDto(pedidoSalvo);
    }
}