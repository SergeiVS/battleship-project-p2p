package org.battleshipprojectp2p.common;

public enum CellValue {
    B("battleship"),
    C("carrier"),
    D("destroyer"),
    E("empty"),
    F("frigate"),
    S("submarine"),
    ;

    private String name;

    CellValue(String name) {
        this.name = name;
    }
}
