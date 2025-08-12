package com.example.demo.service;

import com.example.demo.dtos.CarrinhoResponseDto;
import com.example.demo.dtos.EmailRequestDto;
import com.example.demo.dtos.InserirItemRequestDto;
import com.example.demo.dtos.ItemCarrinhoResponseDto;
import com.example.demo.entities.Carrinho;
import com.example.demo.entities.ItemCarrinho;
import com.example.demo.entities.Produtos;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.CarrinhoNaoEncontradoException;
import com.example.demo.exceptions.ProdutoNaoEncontradoException;
import com.example.demo.exceptions.UsuarioNaoEncontradoException;
import com.example.demo.interfaces.CarrinhoServiceImpl;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.ProdutosRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrinhoService implements CarrinhoServiceImpl {

    private final CarrinhoRepository carrinhoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutosRepository produtosRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, UsuarioRepository usuarioRepository, ProdutosRepository produtosRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtosRepository = produtosRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public CarrinhoResponseDto verCarrinhoUsuario(EmailRequestDto emailRequestDto) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(emailRequestDto.email())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuarioEmail(usuario.getEmail())
                .orElseThrow(() -> new CarrinhoNaoEncontradoException("Carrinho não encontrado para o usuário: " + usuario.getEmail()));


        List<ItemCarrinhoResponseDto> itensDto = carrinho.getItens().stream()
                .map(item -> new ItemCarrinhoResponseDto(
                        item.getProduto().getProdutosId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getProduto().getPreco()
                )).collect(Collectors.toList());


        return new CarrinhoResponseDto(
                carrinho.getCarrinhoId(),
                itensDto,
                carrinho.getValorTotal()
        );
    }

    @Transactional
    @Override
    public CarrinhoResponseDto inserirItemNoCarrinho(InserirItemRequestDto inserirItemRequestDto) {

        Usuario usuario = usuarioRepository.findUsuarioByEmail(inserirItemRequestDto.emailUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuario(usuario)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException("Carrinho não encontrado para o usuario: " + usuario.getEmail()));

        Produtos produtos = produtosRepository.findProdutosByProdutosId(inserirItemRequestDto.id())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        Optional<ItemCarrinho> itemCarrinhoExiste = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getProdutosId().equals(produtos.getProdutosId())).findFirst();

        if (itemCarrinhoExiste.isPresent()) {
            ItemCarrinho itemCarrinho = itemCarrinhoExiste.get();
            itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + inserirItemRequestDto.quantidade());

        } else {
            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setCarrinho(carrinho);
            itemCarrinho.setProduto(produtos);
            itemCarrinho.setQuantidade(inserirItemRequestDto.quantidade());
            carrinho.getItens().add(itemCarrinho);
        }

        recalcularValorTotal(carrinho);

        carrinhoRepository.save(carrinho);

        List<ItemCarrinhoResponseDto> itensDto = carrinho.getItens().stream()
                .map(item -> new ItemCarrinhoResponseDto(
                        item.getProduto().getProdutosId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getProduto().getPreco()
                )).collect(Collectors.toList());

        return new CarrinhoResponseDto(carrinho.getCarrinhoId(), itensDto, carrinho.getValorTotal());

    }

    private void recalcularValorTotal(Carrinho carrinho) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto() != null && item.getProduto().getPreco() != null) {
                BigDecimal precoProduto = item.getProduto().getPreco();
                BigDecimal quantidade = new BigDecimal(item.getQuantidade());
                BigDecimal subtotal = precoProduto.multiply(quantidade);
                valorTotal = valorTotal.add(subtotal);
            }
        }
        carrinho.setValorTotal(valorTotal);
    }
}