package com.ssafy.puzzlepop.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private Long id;
    private String name;
    private int width;
    private int length;


    private int pieceSize;
    private int widthPieceCnt;
    private int lengthPieceCnt;
    private Map<Integer, Integer> levelSize;

    private String encodedString;
    //사진 업로드의 경우
    public void create(int width, int length, String name, int pieceSize, String encodedString) {
        this.width = width;
        this.length = length;
        this.name = name;
        this.encodedString = encodedString;

        this.pieceSize = pieceSize;
        this.levelSize = new HashMap<>();
        this.levelSize.put(1, 500);
        this.levelSize.put(2, 600);
        this.levelSize.put(3, 800);

        int originHeight = this.getLength();
        int originWidth = this.getWidth();
        int imgWidth = originHeight >= originWidth ? Math.round((this.levelSize.get(3)*originWidth) / originHeight / 100) * 100 : this.levelSize.get(3);
        int imgHeight = originHeight >= originWidth ? this.levelSize.get(3) : Math.round((this.levelSize.get(3)*originHeight) /  originWidth/ 100) * 100;
        this.widthPieceCnt = (int) Math.floor((double)imgWidth / (double)this.pieceSize);
        this.lengthPieceCnt = (int) Math.floor((double) imgHeight / (double)this.pieceSize);
    }

    //디폴트
    public static Picture create() {
        Picture p = new Picture();
        p.width = 1000;
        p.length = 551;
        p.encodedString = "짱구.jpg";

        p.pieceSize = 50;
        p.levelSize = new HashMap<>();
        p.levelSize.put(1, 500);
        p.levelSize.put(2, 600);
        p.levelSize.put(3, 800);

        int originHeight = p.getLength();
        int originWidth = p.getWidth();
        int imgWidth = originHeight >= originWidth ? Math.round((p.levelSize.get(3)*originWidth) / originHeight / 100) * 100 : p.levelSize.get(3);
        int imgHeight = originHeight >= originWidth ? p.levelSize.get(3) : Math.round((p.levelSize.get(3)*originHeight) /  originWidth/ 100) * 100;
        p.widthPieceCnt = (int) Math.floor((double)imgWidth / (double)p.pieceSize);
        p.lengthPieceCnt = (int) Math.floor((double) imgHeight / (double)p.pieceSize);

        return p;
    }
}
