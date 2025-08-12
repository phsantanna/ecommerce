package com.example.demo.interfaces;

import com.example.demo.dtos.ItemRequestDto;
import com.example.demo.dtos.ItemResponseDto;

public interface ProdutoServiceImpl {

    ItemResponseDto inserirProdutoNoBanco(ItemRequestDto itemRequestDto);
}
