package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.GameState;

public class StateManager {
    private GameState state;
    private boolean isWon;


    public StateManager() {
        this.state = GameState.SETUP;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState newState) {
        this.state = newState;
    }

    public boolean getIsWon() {
        return this.isWon;
    }

    public void setWon(boolean won) {
        this.isWon = won;
    }
}
