package org.battleshipprojectp2p.game.observer;

public interface Subject<T> {
    void subscribe(Observer<T> observer);

    void unsubscribe(Observer<T> observer);

    void notify(T data);
}
