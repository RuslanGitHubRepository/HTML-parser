package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

public class MakerException extends RuntimeException {
    private String state;
    private Throwable cause;
    public MakerException(final String state, final Throwable cause){
        this.cause = cause;
        this.state = state;
    }
    public MakerException(final String state){
        this.cause = new Throwable();
        this.state = state;
    }
    public String getState() {
        return state;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
