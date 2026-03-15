package org.battleshipprojectp2p.game.observer;

public interface Subject<T> {
    void subscribe(GameObserver<T> observer);

    void unsubscribe(GameObserver<T> observer);

    void notify(T data);
}
