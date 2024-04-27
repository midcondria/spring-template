package com.commerce.team.product;

import com.commerce.team.global.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long price = 10000L;
    private String name;
    private String imageThumbnail = "";
    private String saleState = "selling";
    private Long purchaser = null;

    @Builder
    public Product(String name) {
        this.name = name;
    }
}
