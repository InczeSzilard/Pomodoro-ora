package com.mycompany.pomodoro_ora.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Model tests, without any window.
public class PomodoroModelTest {

    private PomodoroModel model;

    @BeforeEach
    void setUp() {
        model = new PomodoroModel();
        model.beallit(1, 1, 2);   // short times: 1 min work, 1 min short break, 2 min long break
    }

    // Calls tick() n times, as if n seconds passed.
    private void eltelik(int masodperc) {
        for (int i = 0; i < masodperc; i++) {
            model.tick();
        }
    }

    @Test
    void kezdetbenSzunetel() {
        assertEquals(Allapot.SZUNETEL, model.getAllapot());
        assertEquals(60, model.getHatralevo());
        assertEquals(0, model.getKorok());
    }

    @Test
    void startUtanMunka() {
        model.start();
        assertEquals(Allapot.MUNKA, model.getAllapot());
        model.tick();
        assertEquals(59, model.getHatralevo());
    }

    @Test
    void szunetelesAlattNemFogyAzIdo() {
        model.start();
        model.tick();
        model.szunet();
        eltelik(10);
        assertEquals(Allapot.SZUNETEL, model.getAllapot());
        assertEquals(59, model.getHatralevo());   // didn't change while paused
        model.start();
        assertEquals(Allapot.MUNKA, model.getAllapot());   // continues with work
    }

    @Test
    void munkaUtanRovidSzunet() {
        model.start();
        eltelik(60);
        assertEquals(Allapot.ROVID_SZUNET, model.getAllapot());
        assertEquals(1, model.getKorok());
        assertEquals(60, model.getHatralevo());
    }

    @Test
    void szunetUtanUjraMunka() {
        model.start();
        eltelik(60);   // work
        eltelik(60);   // short break
        assertEquals(Allapot.MUNKA, model.getAllapot());
    }

    @Test
    void negyedikKorUtanHosszuSzunet() {
        model.start();
        for (int kor = 1; kor <= 3; kor++) {
            eltelik(60);   // work
            assertEquals(Allapot.ROVID_SZUNET, model.getAllapot());
            eltelik(60);   // short break
        }
        eltelik(60);       // 4th work round
        assertEquals(4, model.getKorok());
        assertEquals(Allapot.HOSSZU_SZUNET, model.getAllapot());
        assertEquals(120, model.getHatralevo());
    }

    @Test
    void resetMindentVisszaallit() {
        model.start();
        eltelik(90);   // work done, half of the break
        model.reset();
        assertEquals(Allapot.SZUNETEL, model.getAllapot());
        assertEquals(60, model.getHatralevo());
        assertEquals(0, model.getKorok());
        model.start();
        assertEquals(Allapot.MUNKA, model.getAllapot());   // starts with work again
    }

    @Test
    void figyeloErtesitest() {
        List<String> esemenyek = new ArrayList<>();
        model.feliratkoz(new PomodoroFigyelo() {
            @Override
            public void tikk(int hatralevoMasodperc) {
                esemenyek.add("tikk " + hatralevoMasodperc);
            }

            @Override
            public void allapotValtozott(Allapot uj) {
                esemenyek.add("allapot " + uj);
            }
        });

        model.start();
        model.tick();

        assertEquals(List.of("allapot MUNKA", "tikk 59"), esemenyek);
    }

    @Test
    void rosszBeallitas() {
        assertThrows(IllegalArgumentException.class, () -> model.beallit(0, 5, 15));
    }
}