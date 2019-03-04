package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
@Service
@Setter
@Getter
@Transactional
public class Pair<A, B> implements Serializable {
    private A first;
    private B second;
    public Pair() {}
    public Pair(A first, B second) {
        super();

        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (  this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    public String toString()
    {
        return first + "(" + second + ")";
    }
}
