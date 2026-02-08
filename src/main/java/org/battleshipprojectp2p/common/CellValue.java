package org.battleshipprojectp2p.common;

import java.util.Arrays;

public enum CellValue {
    B("battleship", 4, 2),
    C("carrier", 5, 1),
    D("destroyer", 2, 4),
    E("empty", 1, 0),
    F("frigate", 3, 3),
    S("submarine", 1, 1),
    X("hit", 1, 0);

    private final String name;
    private final int length;

    private final int maxCount;

    CellValue(String name, int length, int maxCount) {
        this.name = name;
        this.length = length;
        this.maxCount = maxCount;
    }


    public String getName() {
        return this.name;
    }

    public int getLength() {
        return length;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getAllShipsCount() {
        return Arrays.stream(values()).filter(v -> v != E && v != X)
                .map(CellValue::getMaxCount)
                .reduce(0, Integer::sum);
    }
}
