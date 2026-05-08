package org.battleshipprojectp2p.security;

class SequenceService {
    private int sequence = 1;

    protected int getCurrentSequence() {
        return sequence++;
    }
}
