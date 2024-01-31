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
    private String type;
    private String description;
    private Integer price;
    private Long imageId;

    @Builder
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageId = item.getImageId();
        this.price = item.getPrice();
        this.type = item.getType();
    }

    public Item toEntity() {
        return Item.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .imageId(this.imageId)
                .price(this.price)
                .type(this.type)
                .build();
    }
}
