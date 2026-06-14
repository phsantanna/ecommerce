package com.ecommerce.service;

import com.ecommerce.controller.dto.ItemPedidoDtoResponse;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private CarrinhoRepository carrinhoRepository;
    @Mock
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;
    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve listar todos pedidos já realizados pelo usuário")
    void listarPedidosUsuarioComSucesso() {
        UUID idUsuario = UUID.randomUUID();
        List<Pedido> pedidos = new ArrayList<>();
        when(pedidoRepository.findAllByUsuarioId(any(UUID.class))).thenReturn(Optional.of(pedidos));
        when(pedidoMapper.toResponseDtoList(any())).thenReturn(new ArrayList<>());
        List<PedidoResponseDto> pedidoUsuario = pedidoService.listarPedidosUsuario(idUsuario);

        assertNotNull(pedidoUsuario);
        assertEquals(pedidos.size(), pedidoUsuario.size());

        verify(pedidoRepository).findAllByUsuarioId(idUsuario);
        verify(pedidoMapper).toResponseDtoList(pedidos);
    }

    @Test
    @DisplayName("Deve finalizar pedido com sucesso")
    void finalizarPedidoComSucesso() {
        UUID idUsuario = UUID.randomUUID();
        UUID idCarrinho = UUID.randomUUID();
        UUID idPedido = UUID.randomUUID();
        UUID idProduto = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setId(idProduto);
        produto.setPrecoProduto(BigDecimal.valueOf(100));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoUnitario(produto.getPrecoProduto());

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(idCarrinho);
        carrinho.setUsuario(usuario);
        carrinho.setValorTotal(BigDecimal.valueOf(100));

        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
        produtoCarrinho.setProduto(produto);
        produtoCarrinho.setQuantidade(1);
        produtoCarrinho.setCarrinho(carrinho);

        List<ProdutoCarrinho> produtoCarrinhoList = new ArrayList<>();
        produtoCarrinhoList.add(produtoCarrinho);
        carrinho.setProdutosCarrinho(produtoCarrinhoList); // Corrigido: adicionado apenas uma vez

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setUsuario(usuario);
        pedido.setValorTotal(BigDecimal.valueOf(100));
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto(idUsuario);

        List<ItemPedidoDtoResponse> itensResponseSimulados = List.of();

        PedidoResponseDto responseEsperada = new PedidoResponseDto(
                idPedido,
                idUsuario,
                StatusPedido.AGUARDANDO_PAGAMENTO,
                BigDecimal.valueOf(100),
                itensResponseSimulados
        );

        when(usuarioRepository.findUsuarioById(idUsuario)).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuarioId(idUsuario)).thenReturn(Optional.of(carrinho));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoMapper.toResponseDto(any(Pedido.class))).thenReturn(responseEsperada); // CORRIGIDO AQUI

        PedidoResponseDto pedidoResponseDto = pedidoService.finalizarPedido(pedidoRequestDto);

        assertThat(pedidoResponseDto).isNotNull();
        assertThat(pedidoResponseDto.idUsuario()).isEqualTo(idUsuario);

        verify(usuarioRepository).findUsuarioById(idUsuario);
        verify(carrinhoRepository).findCarrinhoByUsuarioId(idUsuario);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(pedidoMapper).toResponseDto(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve retornar excecao ao tentar listar todos pedidos e não encontrar usuário")
    void deveRetornarExcecaoAoListarTodosPedidos(){
        UUID idUsuario = UUID.randomUUID();

        when(pedidoRepository.findAllByUsuarioId(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> pedidoService.listarPedidosUsuario(idUsuario))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário não encontrado.");

        verify(pedidoRepository).findAllByUsuarioId(idUsuario);
        verify(pedidoMapper,never()).toResponseDtoList(any());
    }

    @Test
    @DisplayName("Deve retornar excecao ao tentar finalizar pedido e não encontrar o usuario")
    void deveRetornarExcecaoAoTentarFinalizarPedidoEnaoEncontrarUsuario(){
        UUID idUsuario = UUID.randomUUID();
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto(idUsuario);
        when(usuarioRepository.findUsuarioById(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> pedidoService.finalizarPedido(pedidoRequestDto))
                .isInstanceOf(UsuarioException.class)
                .hasMessage(UsuarioErroTipo.USUARIO_NAO_ENCONTRADO.getMensagem());

        verify(usuarioRepository).findUsuarioById(idUsuario);
        verify(carrinhoRepository, never()).findCarrinhoByUsuarioId(any());
        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toResponseDto(any());
    }

    @Test
    @DisplayName("Deve retornar excecao ao tentar finalizar pedido e não encontrar o carrinho")
    void deveRetornarExcecaoAoTentarFinalizarPedidoENaoEncontrarCarrinho(){
        UUID idUsuario = UUID.randomUUID();
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto(idUsuario);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        when(usuarioRepository.findUsuarioById(any(UUID.class))).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuarioId(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> pedidoService.finalizarPedido(pedidoRequestDto))
                .isInstanceOf(CarrinhoException.class)
                .hasMessage(CarrinhoErroTipo.CARRINHO_NAO_ENCONTRADO.getMensagem());

        verify(usuarioRepository).findUsuarioById(idUsuario);
        verify(carrinhoRepository).findCarrinhoByUsuarioId(idUsuario);
        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toResponseDto(any());
    }



}