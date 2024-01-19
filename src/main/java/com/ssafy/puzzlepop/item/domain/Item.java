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
    private String description;
    private String imagePath;
    private Integer price;
    private String type;

    public void update(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.description = itemDto.getDescription();
        this.imagePath = itemDto.getImagePath();
        this.price = itemDto.getPrice();
        this.type = itemDto.getType();
    }
}
