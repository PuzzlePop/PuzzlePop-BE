package com.ssafy.puzzlepop.item.domain;

import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    List<TeamItem> teamItems;

    public void update(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.type = itemDto.getType();
        this.price = itemDto.getPrice();
        this.description = itemDto.getDescription();
        this.imageId = itemDto.getImageId();
    }
}
