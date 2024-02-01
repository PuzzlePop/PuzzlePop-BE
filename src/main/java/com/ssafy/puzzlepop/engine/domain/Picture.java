package com.ssafy.puzzlepop.engine.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Picture {
    private int width;
    private int length;
    private String encodedString;

    private int pieceSize;
    private int widthPieceCnt;
    private int lengthPieceCnt;
    private Map<Integer, Integer> levelSize;

    public static Picture create() {
        Picture p = new Picture();
        p.width = 1000;
        p.length = 551;
        p.encodedString = "짱구.jpg";

        p.pieceSize = 100;
        p.levelSize = new HashMap<>();
        p.levelSize.put(1, 500);
        p.levelSize.put(2, 600);
        p.levelSize.put(3, 800);

        int originHeight = p.getLength();
        int originWidth = p.getWidth();
        int imgWidth = originHeight >= originWidth ? Math.round((p.levelSize.get(3)*originWidth) / originHeight / 100) * 100 : p.levelSize.get(3);
        int imgHeight = originHeight >= originWidth ? p.levelSize.get(3) : Math.round((p.levelSize.get(3)*originHeight) /  originWidth/ 100) * 100;
        System.out.println("imgHeight = " + imgHeight);
        System.out.println("imgWidth = " + imgWidth);
        p.widthPieceCnt = (int) Math.floor((double)imgWidth / (double)p.pieceSize);
        p.lengthPieceCnt = (int) Math.floor((double) imgHeight / (double)p.pieceSize);

        return p;
    }
}
