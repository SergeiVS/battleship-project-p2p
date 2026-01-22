package org.battleshipprojectp2p.game;

public class Player {
    private  final String name;
    private final boolean isLocal;

    public Player(String name, boolean isLocal) {
        this.name = name;
        this.isLocal = isLocal;
    }

    public String getName() {
        return name;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
