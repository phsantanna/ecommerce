package com.ecommerce.service;

import com.ecommerce.controller.dto.CarrinhoDtoRequest;
import com.ecommerce.controller.dto.CarrinhoDtoResponse;
import com.ecommerce.controller.dto.ProdutoCarrinhoDtoResponse;
import com.ecommerce.controller.dto.UsuarioDtoRequest;
import com.ecommerce.entity.Carrinho;
import com.ecommerce.entity.Produto;
import com.ecommerce.entity.ProdutoCarrinho;
import com.ecommerce.entity.Usuario;
import com.ecommerce.entity.mappers.CarrinhoMapper;
import com.ecommerce.enums.CarrinhoErroTipo;
import com.ecommerce.enums.Categoria;
import com.ecommerce.exceptions.CarrinhoException;
import com.ecommerce.repository.CarrinhoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {

    private Usuario criarUsuarioInstanciado() {
        Usuario usuario = new Usuario();
        usuario.setNome("paulo");
        usuario.setEmail("abc@gmail.com");
        usuario.setCelular("124512521");
        usuario.setId(instanciarUsuarioDtoRequest().id());
        return usuario;
    }

    private Carrinho instanciarCarrinho() {
        return new Carrinho();
    }

    private UsuarioDtoRequest instanciarUsuarioDtoRequest() {
        return new UsuarioDtoRequest(UUID.randomUUID());
    }


    @Mock
    private CarrinhoRepository carrinhoRepository;
    @Mock
    private CarrinhoMapper carrinhoMapper;
    @Mock
    private ProdutoService produtoService;
    @Mock
    private ProdutoCarrinhoService produtoCarrinhoService;
    @InjectMocks
    private CarrinhoService carrinhoService;


    @Test
    void criarCarrinhoComSucesso() {
        Carrinho carrinho = instanciarCarrinho();
        Usuario usuario = criarUsuarioInstanciado();
        carrinho.setUsuario(usuario);
        carrinho.setValorTotal(new BigDecimal(BigInteger.ZERO));
        usuario.setCarrinho(carrinho);

        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        carrinhoService.criarCarrinho(usuario);

        assertThat(carrinho).isNotNull();

        verify(carrinhoRepository, times(1)).save(any(Carrinho.class));
    }


    @Test
    @DisplayName("Deve obter carrinho por usuario com sucesso")
    void obterCarrinhoPorUsuarioComSucesso() {
        UUID usuarioSimuladoId = UUID.randomUUID();
        UUID carrinhoSimuladoID = UUID.randomUUID();
        Carrinho carrinho = instanciarCarrinho();
        Usuario usuario = criarUsuarioInstanciado();
        usuario.setId(usuarioSimuladoId);
        carrinho.setUsuario(usuario);
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinho.setId(carrinhoSimuladoID);

        List<ProdutoCarrinhoDtoResponse> listaProdutosCarrinho = List.of();
        BigDecimal valorTotalSimulado = BigDecimal.ZERO;

        CarrinhoDtoResponse carrinhoDtoResponse = new CarrinhoDtoResponse(carrinhoSimuladoID, listaProdutosCarrinho, valorTotalSimulado);

        when(carrinhoRepository.findCarrinhoByUsuarioId(any(UUID.class))).thenReturn(Optional.of(carrinho));
        when(carrinhoMapper.toDto(any(Carrinho.class))).thenReturn(carrinhoDtoResponse);

        CarrinhoDtoResponse resultado = carrinhoService.obterCarrinhoPorUsuario(instanciarUsuarioDtoRequest());

        assertThat(resultado).isNotNull();
        assertThat(resultado.idCarrinho()).isEqualTo(carrinhoSimuladoID);
        assertThat(resultado.valorTotal()).isEqualByComparingTo(BigDecimal.ZERO);

        verify(carrinhoRepository, times(1)).findCarrinhoByUsuarioId(any(UUID.class));
        verify(carrinhoMapper, times(1)).toDto(any(Carrinho.class));
    }

    @Test
    @DisplayName("Deve inserir produto no carrinho com sucesso e recalcular valor total")
    void inserirNoCarrinhoComSucesso() {
        UUID usuarioSimuladoId = UUID.randomUUID();
        UUID carrinhoSimuladoID = UUID.randomUUID();
        UUID produtoSimuladoId = UUID.randomUUID();
        UUID produtoCarrinhoId = UUID.randomUUID();
        Carrinho carrinho = instanciarCarrinho();
        Usuario usuario = criarUsuarioInstanciado();
        usuario.setId(usuarioSimuladoId);
        carrinho.setUsuario(usuario);
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinho.setId(carrinhoSimuladoID);

        List<ProdutoCarrinho> produtoCarrinho = new ArrayList<>();

        Produto produto = new Produto();
        produto.setId(produtoSimuladoId);
        produto.setQtdProduto(1);
        produto.setCategoriaProduto(Categoria.ALIMENTOS);
        produto.setDescricaoProduto("a");
        produto.setNomeProduto("Arroz");
        produto.setPrecoProduto(BigDecimal.ONE);
        produto.setProdutosCarrinho(produtoCarrinho);

        ProdutoCarrinho produtoCarrinhoSimulado = new ProdutoCarrinho();
        produtoCarrinhoSimulado.setId(produtoCarrinhoId);
        produtoCarrinhoSimulado.setProduto(produto);
        produtoCarrinhoSimulado.setCarrinho(carrinho);
        produtoCarrinhoSimulado.setQuantidade(1);
        produtoCarrinho.add(produtoCarrinhoSimulado);

        BigDecimal valorTotalSimulado = BigDecimal.ONE;

        CarrinhoDtoRequest carrinhoDtoRequest = new CarrinhoDtoRequest(usuarioSimuladoId, produtoSimuladoId);
        CarrinhoDtoResponse carrinhoDtoResponse = new CarrinhoDtoResponse(carrinhoSimuladoID, List.of(), valorTotalSimulado);


        when(carrinhoRepository.findCarrinhoByUsuarioId(any(UUID.class))).thenReturn(Optional.of(carrinho));
        when(produtoService.buscarProdutoPorId(any(UUID.class))).thenReturn(produto);
        when(produtoCarrinhoService.adicionarProdutoCarrinho(any(Produto.class), any(Carrinho.class))).thenReturn(produtoCarrinhoSimulado);
        when(carrinhoMapper.toDto(any(Carrinho.class))).thenReturn(carrinhoDtoResponse);

        CarrinhoDtoResponse carrinho1 = carrinhoService.inserirNoCarrinho(carrinhoDtoRequest);

        assertThat(carrinho1).isNotNull();
        assertThat(carrinho1.idCarrinho()).isEqualTo(carrinhoSimuladoID);
        assertThat(carrinho1.valorTotal()).isEqualByComparingTo(BigDecimal.ONE);


        verify(carrinhoRepository, times(1)).findCarrinhoByUsuarioId(usuarioSimuladoId);
        verify(produtoService, times(1)).buscarProdutoPorId(produtoSimuladoId);
        verify(produtoCarrinhoService, times(1)).adicionarProdutoCarrinho(any(Produto.class), any(Carrinho.class));
        verify(carrinhoMapper, times(1)).toDto(any(Carrinho.class));


    }

    @Test
    void deveRetornarExcecaoAoTentarCriarCarrinhoEJaExistente() {
        UUID usuarioSimuladoId = UUID.randomUUID();
        Usuario usuario = criarUsuarioInstanciado();
        usuario.setId(usuarioSimuladoId);

        when(carrinhoRepository.existsByUsuarioId(any(UUID.class))).thenReturn(true);
        assertThatThrownBy(() -> carrinhoService.criarCarrinho(usuario))
                .isInstanceOf(CarrinhoException.class)
                .hasMessage(CarrinhoErroTipo.CARRINHO_JA_EXISTENTE.getMensagem());

        verify(carrinhoRepository, times(1)).existsByUsuarioId(any(UUID.class));
        verify(carrinhoRepository, never()).save(any(Carrinho.class));
    }


    @Test
    void deveRetornarExcecaoAoTentarObterCarrinhoPorUsuarioEUsuarioNaoExistir() {
        UUID usuarioSimuladoId = UUID.randomUUID();
        UsuarioDtoRequest usuarioDtoRequest = new UsuarioDtoRequest(usuarioSimuladoId);

        when(carrinhoRepository.findCarrinhoByUsuarioId(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carrinhoService.obterCarrinhoPorUsuario(usuarioDtoRequest))
                .isInstanceOf(CarrinhoException.class)
                .hasMessage(CarrinhoErroTipo.USUARIO_NAO_ENCONTRADO.getMensagem());

        verify(carrinhoRepository, times(1)).findCarrinhoByUsuarioId(usuarioSimuladoId);
        verify(carrinhoRepository, never()).save(any(Carrinho.class));
    }

    @Test
    void deveRetornarExcecaoAoTentarInserirProdutoNoCarrinhoENaoEncontrarCarrinho() {
        UUID usuarioSimuladoId = UUID.randomUUID();
        UsuarioDtoRequest usuarioDtoRequest = new UsuarioDtoRequest(usuarioSimuladoId);

        when(carrinhoRepository.findCarrinhoByUsuarioId(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carrinhoService.obterCarrinhoPorUsuario(usuarioDtoRequest))
                .isInstanceOf(CarrinhoException.class)
                .hasMessage(CarrinhoErroTipo.USUARIO_NAO_ENCONTRADO.getMensagem());

        verify(carrinhoRepository, times(1)).findCarrinhoByUsuarioId(usuarioSimuladoId);
        verify(produtoService, never()).buscarProdutoPorId(any(UUID.class));
        verify(produtoCarrinhoService, never()).adicionarProdutoCarrinho(any(Produto.class), any(Carrinho.class));
        verify(carrinhoMapper, never()).toDto(any(Carrinho.class));

    }
}