package com.commerce.team.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInfo {

    private Long id;
    private Long price;
    private String name;
    private String imageThumbnail;
    private String saleState;
    private Long purchaser;

    public ProductInfo(Long id, Long price, String name, String imageThumbnail, String saleState, Long purchaser) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageThumbnail = imageThumbnail;
        this.saleState = saleState;
        this.purchaser = purchaser;
    }
}