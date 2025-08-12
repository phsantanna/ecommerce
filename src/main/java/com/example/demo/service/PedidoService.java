package com.example.demo.service;

import com.example.demo.dtos.ListaPedidosRequestDto;
import com.example.demo.dtos.PedidoRequestDto;
import com.example.demo.dtos.PedidoResponseDto;
import com.example.demo.entities.Carrinho;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Usuario;
import com.example.demo.enums.StatusPedido;
import com.example.demo.exceptions.CarrinhoNaoEncontradoException;
import com.example.demo.exceptions.CarrinhoVazioException;
import com.example.demo.exceptions.UsuarioNaoEncontradoException;
import com.example.demo.interfaces.PedidoServiceImpl;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService implements PedidoServiceImpl {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarrinhoRepository carrinhoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, CarrinhoRepository carrinhoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.carrinhoRepository = carrinhoRepository;
    }

    @Transactional
    @Override
    public PedidoResponseDto realizarPedido(PedidoRequestDto emailUsuario) {

        Usuario usuario = usuarioRepository.findUsuarioByEmail(emailUsuario.emailUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuario(usuario)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException("Carrinho não encontrado"));

        if(carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio. Não é possível criar um pedido.");
        }

        Pedido novoPedido = new Pedido();
        novoPedido.setUsuario(usuario);
        novoPedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        novoPedido.setValorTotal(usuario.getCarrinho().getValorTotal());

        pedidoRepository.save(novoPedido);

        return new PedidoResponseDto(novoPedido.getId(), novoPedido.getUsuario().getUsuarioId(), novoPedido.getUsuario().getEmail(),
                novoPedido.getStatus(), novoPedido.getValorTotal());

    }

    @Override
    public List<PedidoResponseDto> listarTodosPedidosUsuario(ListaPedidosRequestDto emailUsuario) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(emailUsuario.emailUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));


        return usuario.getPedidos().stream().map(
                pedido -> new PedidoResponseDto(pedido.getId(),
                        pedido.getUsuario().getUsuarioId(),
                        pedido.getUsuario().getEmail(),
                        pedido.getStatus(),
                        pedido.getValorTotal())
        ).collect(Collectors.toList());
    }
}
