package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.GameState;

public class StateManager {
    GameState state;

    public StateManager() {
        this.state = GameState.SETUP;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState newState) {
        this.state = newState;
    }
}
