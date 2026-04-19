package org.battleshipprojectp2p.game.observer;

import org.battleshipprojectp2p.game.gameDto.GameData;

import java.util.HashSet;
import java.util.Set;

public class GameSubject implements Subject<GameData> {
    private final Set<GameObserver<GameData>> observers = new HashSet<>();

    @Override
    public void subscribe(GameObserver<GameData> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(GameObserver<GameData> observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(GameData data) {
        observers.forEach(observer -> {
            observer.update(data);
        });
    }
}
