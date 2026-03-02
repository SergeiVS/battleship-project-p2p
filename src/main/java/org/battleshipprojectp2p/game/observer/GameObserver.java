package org.battleshipprojectp2p.game.observer;

public interface GameObserver<T> {
    void update(T data);
}
