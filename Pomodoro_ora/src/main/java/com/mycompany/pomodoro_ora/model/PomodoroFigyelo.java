package com.mycompany.pomodoro_ora.model;

// Observer: anyone who wants to know what the model is doing implements this.
// Both methods are "default" (empty), so a listener only overrides what it needs.
public interface PomodoroFigyelo {

    // Called every time the remaining seconds change.
    default void tikk(int hatralevoMasodperc) {
    }

    // Called when the state changes (work, break, paused...).
    default void allapotValtozott(Allapot uj) {
    }
}