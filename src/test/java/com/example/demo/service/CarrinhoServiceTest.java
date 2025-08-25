package com.example.demo.service;

import com.example.demo.dtos.CarrinhoResponseDto;
import com.example.demo.dtos.EmailRequestDto;
import com.example.demo.dtos.InserirItemRequestDto;
import com.example.demo.entities.Carrinho;
import com.example.demo.entities.ItemCarrinho;
import com.example.demo.entities.Produtos;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ProdutoNaoEncontradoException;
import com.example.demo.exceptions.UsuarioNaoEncontradoException;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.ProdutosRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProdutosRepository produtosRepository;

    private Usuario usuario;
    private Carrinho carrinho;
    private Produtos produto1, produto2;
    private InserirItemRequestDto inserirItemRequestDto1, inserirItemRequestDto2;
    private EmailRequestDto emailRequestDto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "teste@gmail.com", "senha123");
        carrinho = new Carrinho();
        carrinho.setCarrinhoId(10L);
        carrinho.setUsuario(usuario);
        carrinho.setItens(new ArrayList<>());

        produto1 = new Produtos(100L, "Notebook", new BigDecimal("7500.00"),"Notebook","Notebook");
        produto2 = new Produtos(101L, "Iphone 16", new BigDecimal("7000.00"),"Iphone 16","Celular");

        emailRequestDto = new EmailRequestDto(usuario.getEmail());
        inserirItemRequestDto1 = new InserirItemRequestDto(produto1.getProdutosId(), usuario.getEmail(), 1);
        inserirItemRequestDto2 = new InserirItemRequestDto(produto2.getProdutosId(), usuario.getEmail(), 1);

    }


    @Test
    @DisplayName("Retorna o carrinho do usuário com sucesso.")
    void verCarrinhoUsuario_QuandoUsuarioECarrinhoExistem_DeveRetornarDto() {

        ItemCarrinho itemCarrinho = new ItemCarrinho(1L, carrinho, produto1, 2);
        carrinho.getItens().add(itemCarrinho);
        carrinho.setValorTotal(new BigDecimal("15000.00"));

        when(usuarioRepository.findUsuarioByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuarioEmail(usuario.getEmail())).thenReturn(Optional.of(carrinho));

        CarrinhoResponseDto carrinhoResponseDto = carrinhoService.verCarrinhoUsuario(emailRequestDto);

        assertNotNull(carrinhoResponseDto);
        assertEquals(carrinho.getCarrinhoId(), carrinhoResponseDto.id());
        assertEquals("Notebook", carrinhoResponseDto.itemCarrinhoList().get(0).nomeProduto());
        assertEquals(2,carrinhoResponseDto.itemCarrinhoList().get(0).quantidade());
        assertEquals(0, new BigDecimal("15000.00").compareTo(carrinhoResponseDto.valorTotal()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao ver carrinho se usuário não for encontrado")
    void verCarrinhoUsuario_QuandoUsuarioNaoExiste_DeveLancarExcecao() {
        when(usuarioRepository.findUsuarioByEmail(anyString())).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> carrinhoService.verCarrinhoUsuario(emailRequestDto));

        assertEquals("Usuario não encontrado",exception.getMessage());

        verify(carrinhoRepository, never()).findCarrinhoByUsuarioEmail(any());
    }

    @Test
    @DisplayName("Deve atualizar a quantidade de um item existente no carrinho")
    void inserirItemNoCarrinho_QuandoItemJaExiste_DeveAtualizarQuantidade() {

        ItemCarrinho itemExiste =  new ItemCarrinho(1L, carrinho, produto1, 1);
        carrinho.getItens().add(itemExiste);

        when(usuarioRepository.findUsuarioByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuario(usuario)).thenReturn(Optional.of(carrinho));
        when(produtosRepository.findProdutosByProdutosId(produto1.getProdutosId())).thenReturn(Optional.of(produto1));
        when(carrinhoRepository.save(any(Carrinho.class))).thenAnswer(invocation ->{
            return invocation.<Carrinho>getArgument(0);
        });
        InserirItemRequestDto dtoComMais2Itens = new InserirItemRequestDto(produto1.getProdutosId(), usuario.getEmail(),2 );

        CarrinhoResponseDto carrinhoResponseDto = carrinhoService.inserirItemNoCarrinho(dtoComMais2Itens);

        ArgumentCaptor<Carrinho> carrinhoCaptor = ArgumentCaptor.forClass(Carrinho.class);
        verify(carrinhoRepository).save(carrinhoCaptor.capture());

        Carrinho carrinhoSalvo = carrinhoCaptor.getValue();
        assertEquals(1, carrinhoSalvo.getItens().size());
        assertEquals(3, carrinhoSalvo.getItens().get(0).getQuantidade());
        assertEquals(0, new BigDecimal("22500.00").compareTo(carrinhoSalvo.getValorTotal()));

        assertNotNull(carrinhoResponseDto);
        assertEquals(10L, carrinhoResponseDto.id());
        assertEquals(carrinho.getCarrinhoId(), carrinhoResponseDto.id());

    }

    @Test
    @DisplayName("Deve inserir um novo item no carrinho")
    void inserirItemNoCarrinho_QuandoItemNaoExiste_DeveAdicionarItem(){
        when(usuarioRepository.findUsuarioByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuario(usuario)).thenReturn(Optional.of(carrinho));
        when(produtosRepository.findProdutosByProdutosId(produto2.getProdutosId())).thenReturn(Optional.of(produto2));

        carrinhoService.inserirItemNoCarrinho(inserirItemRequestDto2);

        ArgumentCaptor<Carrinho> carrinhoCaptor = ArgumentCaptor.forClass(Carrinho.class);
        verify(carrinhoRepository).save(carrinhoCaptor.capture());

        Carrinho carrinhoSalvo = carrinhoCaptor.getValue();
        assertNotNull(carrinhoSalvo);
        assertEquals(1,carrinhoSalvo.getItens().size());
        assertEquals(1,carrinhoSalvo.getItens().get(0).getQuantidade());
        assertEquals("Iphone 16",carrinhoSalvo.getItens().get(0).getProduto().getNome());
        assertEquals(0, new BigDecimal(7000).compareTo(carrinhoSalvo.getValorTotal()));

    }

    @Test
    @DisplayName("Deve lançar exceção ao inserir item se produto não for encontrado")
    void inserirItemNoCarrinho_QuandoProdutoNaoExiste_DeveLancarExcecao() {

        when(usuarioRepository.findUsuarioByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.findCarrinhoByUsuario(usuario)).thenReturn(Optional.of(carrinho));

        when(produtosRepository.findProdutosByProdutosId(anyLong())).thenReturn(Optional.empty());

        ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, () -> {
            carrinhoService.inserirItemNoCarrinho(inserirItemRequestDto1);
        });

        assertEquals("Produto não encontrado",exception.getMessage());
    }

}