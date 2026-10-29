package com.mycompany.pomodoro_ora.model;

import java.util.ArrayList;
import java.util.List;

// The logic of the timer. It knows nothing about buttons or windows (no Swing imports).
public class PomodoroModel {

    // Everyone who listens to the model (for example the window).
    private final List<PomodoroFigyelo> figyelok = new ArrayList<>();

    private Allapot allapot = Allapot.SZUNETEL;
    private Allapot folytatando = Allapot.MUNKA;   // what to continue with after a pause
    private int hatralevo;                           // remaining seconds
    private int korok = 0;                           // finished work rounds

    // Lengths in minutes (can be changed in the settings).
    private int munkaPerc = 25;
    private int rovidSzunetPerc = 5;
    private int hosszuSzunetPerc = 15;

    public PomodoroModel() {
        hatralevo = munkaPerc * 60;
    }

    // ---------- observer ----------

    public void feliratkoz(PomodoroFigyelo f) {
        figyelok.add(f);
    }

    public void leiratkoz(PomodoroFigyelo f) {
        figyelok.remove(f);
    }

    private void ertesitTikk() {
        for (PomodoroFigyelo f : figyelok) {
            f.tikk(hatralevo);
        }
    }

    private void ertesitAllapot() {
        for (PomodoroFigyelo f : figyelok) {
            f.allapotValtozott(allapot);
        }
    }

    // ---------- buttons ----------

    // Start, or continue after a pause.
    public void start() {
        if (allapot == Allapot.SZUNETEL) {
            allapot = folytatando;
            ertesitAllapot();
        }
    }

    // Pause: remember where we were, then stop.
    public void szunet() {
        if (allapot != Allapot.SZUNETEL) {
            folytatando = allapot;
            allapot = Allapot.SZUNETEL;
            ertesitAllapot();
        }
    }

    // Back to the beginning: paused, full work time, 0 rounds.
    public void reset() {
        allapot = Allapot.SZUNETEL;
        folytatando = Allapot.MUNKA;
        hatralevo = munkaPerc * 60;
        korok = 0;
        ertesitAllapot();
        ertesitTikk();
    }

    // ---------- time ----------

    // One second passed. The controller's Timer calls this.
    public void tick() {
        if (allapot == Allapot.SZUNETEL) {
            return;   // paused, nothing happens
        }
        hatralevo--;
        ertesitTikk();
        if (hatralevo == 0) {
            kovetkezo();
        }
    }

    // The current phase is over, move to the next one.
    private void kovetkezo() {
        if (allapot == Allapot.MUNKA) {
            korok++;
            if (korok % 4 == 0) {
                allapot = Allapot.HOSSZU_SZUNET;   // every 4th round: long break
                hatralevo = hosszuSzunetPerc * 60;
            } else {
                allapot = Allapot.ROVID_SZUNET;
                hatralevo = rovidSzunetPerc * 60;
            }
        } else {
            allapot = Allapot.MUNKA;               // after any break: work again
            hatralevo = munkaPerc * 60;
        }
        ertesitAllapot();
        ertesitTikk();
    }

    // ---------- settings ----------

    // New lengths in minutes. After changing them we start over.
    public void beallit(int munka, int rovidSzunet, int hosszuSzunet) {
        if (munka <= 0 || rovidSzunet <= 0 || hosszuSzunet <= 0) {
            throw new IllegalArgumentException("Az idoknek pozitivnak kell lenniuk");
        }
        munkaPerc = munka;
        rovidSzunetPerc = rovidSzunet;
        hosszuSzunetPerc = hosszuSzunet;
        reset();
    }

    // ---------- getters ----------

    public Allapot getAllapot() {
        return allapot;
    }

    public int getHatralevo() {
        return hatralevo;
    }

    public int getKorok() {
        return korok;
    }

    public int getMunkaPerc() {
        return munkaPerc;
    }

    public int getRovidSzunetPerc() {
        return rovidSzunetPerc;
    }

    public int getHosszuSzunetPerc() {
        return hosszuSzunetPerc;
    }
}