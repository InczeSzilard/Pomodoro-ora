package com.mycompany.pomodoro_ora.model;

// The possible states of the timer, each with the text shown to the user.
public enum Allapot {
    MUNKA("Munka"),                     // work
    ROVID_SZUNET("Rovid szunet"),       // short break
    HOSSZU_SZUNET("Hosszu szunet"),     // long break (after every 4th round)
    SZUNETEL("Szuneteltetve");          // paused, the clock doesn't move

    private final String felirat;

    Allapot(String felirat) {
        this.felirat = felirat;
    }

    public String getFelirat() {
        return felirat;
    }
}