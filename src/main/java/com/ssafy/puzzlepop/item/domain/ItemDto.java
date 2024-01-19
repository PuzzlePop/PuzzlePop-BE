package com.ssafy.puzzlepop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
    private Integer price;
    private String type;

    @Builder
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.imagePath = item.getImagePath();
        this.price = item.getPrice();
        this.type = item.getType();
    }

    public Item toEntity() {
        return Item.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .imagePath(this.imagePath)
                .price(this.price)
                .type(this.type)
                .build();
    }
}
