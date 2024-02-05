package com.ssafy.puzzlepop.engine.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DropItem extends Item {
    private String uuid;
    private double position_x;
    private double position_y;

    public DropItem(ItemType name, String uuid, int x, int y) {
        super(name);
        this.uuid = uuid;
        this.position_x = x;
        this.position_y = y;
    }

    public static DropItem randomCreate() {
        Random random = new Random();
        int position_x = random.nextInt(2580);
        int position_y = random.nextInt(1440);
        String uuid = String.valueOf(UUID.randomUUID());

        //공격형 아이템만 추첨 리스트에 추가
        List<ItemType> list = new LinkedList<>();
        list.add(ItemType.EARTHQUAKE);
        list.add(ItemType.FIRE);
        list.add(ItemType.ROCKET);

        return new DropItem(
                list.get(random.nextInt(list.size())), uuid, position_x, position_y);
    }

    @Override
    public String toString() {
        return super.toString()+" (position_x=" +position_x + ", position_y=" + position_y + ")";
    }
}

