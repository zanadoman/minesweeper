package org.example;

public final class Mine implements Cloneable {
    public Mine(boolean hasMine) {
        this.hasMine = hasMine;
        setNeighbourCount(0);
        _isFlagged = false;
        _isCleared = false;
    }

    public int getNeighbourCount() {
        return _neighbourCount;
    }

    public void setNeighbourCount(int neighbourCount) {
        _neighbourCount = neighbourCount;
    }

    public boolean IsFlagged() {
        return _isFlagged;
    }

    public void flag() {
        _isFlagged = !IsFlagged();
    }

    public boolean IsCleared() {
        return _isCleared;
    }

    public void clear() {
        _isCleared = true;
    }

    public Object clone() {
        return new Mine(hasMine);
    }

    public final boolean hasMine;
    private int _neighbourCount;
    private boolean _isFlagged;
    private boolean _isCleared;
}
