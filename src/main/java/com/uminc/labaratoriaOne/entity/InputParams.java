package com.uminc.labaratoriaOne.entity;

import java.util.Objects;

public class InputParams {
    private int first;
    private int second;
    private int third;

    public InputParams(int first, int second, int third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputParams that = (InputParams) o;
        return first == that.first && second == that.second && third == that.third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
