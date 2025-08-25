package com.example.demo.service;

import com.example.demo.dtos.PedidoRequestDto;
import com.example.demo.dtos.PedidoResponseDto;
import com.example.demo.entities.*;
import com.example.demo.enums.StatusPedido;
import com.example.demo.exceptions.CarrinhoVazioException;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock private CarrinhoRepository carrinhoRepository;


    private Usuario usuarioCarrinhoCheio, usuarioCarrinhoVazio;
    private Carrinho carrinhoCheio,carrinhoVazio;
    private Produtos produto1;
    private PedidoRequestDto pedidoRequestDtoCarrinhoCheio, pedidoRequestDtoCarrinhoVazio;
    private Pedido pedido;
    private PedidoResponseDto pedidoResponseDto;
    private ItemCarrinho item;

    @BeforeEach
    void setUp() {
        usuarioCarrinhoCheio = new Usuario();
        usuarioCarrinhoCheio.setUsuarioId(1L);
        usuarioCarrinhoCheio.setEmail("teste@gmail.com");

        carrinhoCheio = new Carrinho();
        carrinhoCheio.setCarrinhoId(10L);
        carrinhoCheio.setItens(new ArrayList<>());

        produto1 = new Produtos();
        produto1.setProdutosId(100L);
        produto1.setNome("Notebook");
        produto1.setPreco(new BigDecimal("5000.00"));

        item = new ItemCarrinho();
        item.setProduto(produto1);
        item.setQuantidade(1);
        carrinhoCheio.getItens().add(item);

        usuarioCarrinhoCheio.setCarrinho(carrinhoCheio);

        pedidoRequestDtoCarrinhoCheio = new PedidoRequestDto(usuarioCarrinhoCheio.getEmail());

        pedido = new Pedido();
        pedido.setUsuario(usuarioCarrinhoCheio);
        pedido.setId(1L);

        usuarioCarrinhoVazio = new Usuario();
        usuarioCarrinhoVazio.setUsuarioId(2L);
        usuarioCarrinhoVazio.setEmail("abc@gmail.com");

        carrinhoVazio = new Carrinho();
        carrinhoVazio.setCarrinhoId(10L);
        carrinhoVazio.setUsuario(usuarioCarrinhoVazio);

        usuarioCarrinhoVazio.setCarrinho(carrinhoVazio);
        pedidoRequestDtoCarrinhoVazio = new PedidoRequestDto(usuarioCarrinhoVazio.getEmail());
    }

    @Test
    @DisplayName("Realiza o pedido e salva no banco")
    void realizarPedido_DeveSalvarPedidoCorretamente() {
        when(usuarioRepository.findUsuarioByEmail(usuarioCarrinhoCheio.getEmail())).thenReturn(Optional.of(usuarioCarrinhoCheio));
        when(carrinhoRepository.findCarrinhoByUsuario(usuarioCarrinhoCheio)).thenReturn(Optional.of(carrinhoCheio));

        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedidoParaSalvar = invocation.getArgument(0);
            pedidoParaSalvar.setId(1L);
            return pedidoParaSalvar;
        });

        PedidoResponseDto response = pedidoService.realizarPedido(pedidoRequestDtoCarrinhoCheio);

        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);

        verify(pedidoRepository).save(pedidoCaptor.capture());

        Pedido pedidoCapturado = pedidoCaptor.getValue();

        assertNotNull(pedidoCapturado);
        assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, pedidoCapturado.getStatus());
        assertEquals(usuarioCarrinhoCheio.getUsuarioId(), pedidoCapturado.getUsuario().getUsuarioId());

        assertNotNull(response);
        assertEquals(1L, response.pedidoId());
        assertEquals(pedido.getId(), response.pedidoId());
    }

    @Test
    @DisplayName("Lança exceção quando o carrinho está vazio e tentam realizar pedido")
    void realizarPedido_DeveRetornarExcecaoSeCarrinhoEstiverVazio() {

        when(usuarioRepository.findUsuarioByEmail(usuarioCarrinhoVazio.getEmail())).thenReturn(Optional.of(usuarioCarrinhoVazio));
        when(carrinhoRepository.findCarrinhoByUsuario(usuarioCarrinhoVazio)).thenReturn(Optional.of(usuarioCarrinhoVazio.getCarrinho()));

        CarrinhoVazioException exception = assertThrows(CarrinhoVazioException.class, () -> {
            pedidoService.realizarPedido(pedidoRequestDtoCarrinhoVazio);
        });

        verify(usuarioRepository, times(1)).findUsuarioByEmail(usuarioCarrinhoVazio.getEmail());
        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(carrinhoRepository, times(1)).findCarrinhoByUsuario(usuarioCarrinhoVazio);


        assertEquals("O carrinho está vazio. Não é possível criar um pedido.", exception.getMessage());
    }


}