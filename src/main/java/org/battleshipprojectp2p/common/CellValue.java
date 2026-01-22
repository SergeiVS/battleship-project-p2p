package org.battleshipprojectp2p.common;

public enum CellValue {
    B("battleship", 4),
    C("carrier", 5),
    D("destroyer", 2),
    E("empty", 1),
    F("frigate", 3),
    S("submarine", 1),
    X("hit", 1);

    private String name;
    private int length;

    CellValue(String name, int length) {
        this.name = name;
        this.length = length;
    }
}
