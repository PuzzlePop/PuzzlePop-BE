package com.ssafy.puzzlepop.engine.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class DropItem extends Item {
    private double position_x;
    private double position_y;

    public DropItem(ItemType name, int x, int y) {
        super(name);
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
        List<ItemType> list = new LinkedList<>();
        list.add(ItemType.EARTHQUAKE);
        list.add(ItemType.FIRE);
        list.add(ItemType.ROCKET);
        System.out.println(list.get(random.nextInt(3)));
    }
}

