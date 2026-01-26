package org.battleshipprojectp2p.common;

public enum CellValue {
    B("battleship", 4, 1),
    C("carrier", 5,1),
    D("destroyer", 2,3),
    E("empty", 1,0),
    F("frigate", 3,2),
    S("submarine", 1,4),
    X("hit", 1,1);

    private String name;
    private int length;

    private int count;

    CellValue(String name, int length, int count) {
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
