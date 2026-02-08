package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackSide;

import java.util.List;

public class AttackSideManager {
    private AttackSide currentSide;


    public AttackSide getCurrentSide() {
        return currentSide;
    }

    public void setCurrentSide(AttackSide currentSide) {
        if (this.currentSide == null) {
            this.currentSide = currentSide;
        }
    }

    public void changeSide() {
        List<AttackSide> sides = List.of(AttackSide.values());
        this.currentSide = sides.get((sides.indexOf(currentSide) + 1) % sides.size());
    }
}
