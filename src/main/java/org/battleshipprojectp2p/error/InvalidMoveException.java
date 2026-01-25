package org.battleshipprojectp2p.error;

public class InvalidMoveException extends RuntimeException{
    public InvalidMoveException(String message) {
        super(message);
    }
}
