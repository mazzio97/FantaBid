package org.fantabid.model;

import java.util.Optional;

public final class Pair<F, S> {

    private final F first;
    private final S second;
    
    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }
    
    public static <X, Y> Pair<X, Y> of(final X first, final Y second) {
        return new Pair<>(first, second);
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public F getKey() {
        return getFirst();
    }

    public S getValue() {
        return getSecond();
    }

    public Pair<F, S> getCopy() {
        return new Pair<>(getFirst(), getSecond());
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return Optional.ofNullable(obj)
                       .filter(o -> Pair.class.isInstance(o))
                       .map(o -> (Pair<?, ?>) o)
                       .filter(o -> getFirst().equals(o.getFirst()))
                       .filter(o -> getSecond().equals(o.getSecond()))
                       .isPresent();
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
