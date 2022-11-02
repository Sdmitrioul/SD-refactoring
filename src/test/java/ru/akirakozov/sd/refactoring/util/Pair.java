package ru.akirakozov.sd.refactoring.util;

public final class Pair<F, S> {
    private final F first;
    private final S second;
    
    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }
    
    public F getFirst() {
        return first;
    }
    
    public S getSecond() {
        return second;
    }
}
