package org.battleshipprojectp2p.game.observer;

import org.battleshipprojectp2p.game.gameDto.GameData;

import java.util.HashSet;
import java.util.Set;

public class GameSubject implements Subject<GameData> {
    private final Set<GameObserver<GameData>> observers = new HashSet<>();

    @Override
    public void subscribe(GameObserver<GameData> observer) {
        IO.println("Observer subscribed: " + observer.getClass());
        observers.add(observer);
    }

    @Override
    public void unsubscribe(GameObserver<GameData> observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(GameData data) {
        IO.println("Observer notified observers: " + observers.size());

        observers.forEach(observer -> {
            IO.println("Observer notified: " + observer.getClass());
            observer.update(data);
        });
    }
}
