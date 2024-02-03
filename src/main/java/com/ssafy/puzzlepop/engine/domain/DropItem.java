package com.ssafy.puzzlepop.engine.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@EqualsAndHashCode(callSuper = true)
@Data
public class DropItem extends Item {
    private double position_x;
    private double position_y;

    public DropItem(ItemType name, int x, int y) {
        super(name);
        position_x = x;
        position_y = y;
    }

    public static DropItem randomCreate() {
        Random random = new Random();
        int position_x = random.nextInt(2580);
        int position_y = random.nextInt(1440);

        //공격형 아이템만 추첨 리스트에 추가
        List<ItemType> list = new LinkedList<>();
        list.add(ItemType.EARTHQUAKE);
        list.add(ItemType.FIRE);
        list.add(ItemType.ROCKET);

        return new DropItem(
                list.get(random.nextInt(list.size())), position_x, position_y);
    }

    public static void main(String[] args) {
        Random random = new Random();
        int position_x = random.nextInt(2580);
        int position_y = random.nextInt(1440);
        List<ItemType> list = new LinkedList<>();
        list.add(ItemType.EARTHQUAKE);
        list.add(ItemType.FIRE);
        list.add(ItemType.ROCKET);
        System.out.println(list.get(random.nextInt(3)));

        DropItem item = new DropItem(
                list.get(random.nextInt(list.size())), position_x, position_y);

        System.out.println(item);
    }

    @Override
    public String toString() {
        return super.toString()+" (position_x=" +position_x + ", position_y=" + position_y + ")";
    }
}

