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
    private int imgWidth;
    private int imgHeight;


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
        this.levelSize.put(1, 400);
        this.levelSize.put(2, 500);
        this.levelSize.put(3, 600);

        int originHeight = this.getLength();
        int originWidth = this.getWidth();
        this.imgWidth = originHeight >= originWidth ? Math.round((this.levelSize.get(1)*originWidth) / originHeight / 100) * 100 : this.levelSize.get(1);
        this.imgHeight = originHeight >= originWidth ? this.levelSize.get(1) : Math.round((this.levelSize.get(1)*originHeight) /  originWidth/ 100) * 100;
        this.widthPieceCnt = (int) Math.floor((double)this.imgWidth / (double)this.pieceSize);
        this.lengthPieceCnt = (int) Math.floor((double) this.imgHeight / (double)this.pieceSize);
    }

    //디폴트
    public static Picture create() {
        Picture p = new Picture();
        p.width = 1000;
        p.length = 551;
        p.encodedString = "짱구.jpg";

        p.pieceSize = 40;
        p.levelSize = new HashMap<>();
        p.levelSize.put(1, 400);
        p.levelSize.put(2, 500);
        p.levelSize.put(3, 600);

        int originHeight = p.getLength();
        int originWidth = p.getWidth();
        p.imgWidth = originHeight >= originWidth ? Math.round((p.levelSize.get(1)*originWidth) / originHeight / 100) * 100 : p.levelSize.get(1);
        p.imgHeight = originHeight >= originWidth ? p.levelSize.get(1) : Math.round((p.levelSize.get(1)*originHeight) /  originWidth/ 100) * 100;
        p.widthPieceCnt = (int) Math.floor((double)p.imgWidth / (double)p.pieceSize);
        p.lengthPieceCnt = (int) Math.floor((double)p.imgHeight / (double)p.pieceSize);

        return p;
    }
}
