package com.ssafy.puzzlepop.item.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String description;
    private Integer price;
    private Long imageId;

    public void update(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.type = itemDto.getType();
        this.price = itemDto.getPrice();
        this.description = itemDto.getDescription();
        this.imageId = itemDto.getImageId();
    }
}
