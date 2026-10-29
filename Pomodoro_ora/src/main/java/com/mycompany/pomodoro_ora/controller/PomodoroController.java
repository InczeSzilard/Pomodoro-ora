package com.mycompany.pomodoro_ora.controller;

import com.mycompany.pomodoro_ora.model.PomodoroModel;
import com.mycompany.pomodoro_ora.view.BeallitasokDialog;
import com.mycompany.pomodoro_ora.view.PomodoroView;
import javax.swing.Timer;

// Connects the window and the model. All button listeners are here.
public class PomodoroController {

    private final PomodoroModel model;
    private final PomodoroView view;

    public PomodoroController(PomodoroModel model, PomodoroView view) {
        this.model = model;
        this.view = view;

        // the window listens to the model
        model.feliratkoz(view);

        // show the starting values
        view.tikk(model.getHatralevo());
        view.allapotValtozott(model.getAllapot());

        // buttons: they only call model methods
        view.getStartGomb().addActionListener(e -> model.start());
        view.getSzunetGomb().addActionListener(e -> model.szunet());
        view.getResetGomb().addActionListener(e -> model.reset());
        view.getBeallitasGomb().addActionListener(e -> beallitasok());

        // javax.swing.Timer: calls model.tick() every 1000 ms on the Swing thread.
        // (Thread.sleep would freeze the window.)
        Timer timer = new Timer(1000, e -> model.tick());
        timer.start();
    }

    // Opens the settings window.
    private void beallitasok() {
        BeallitasokDialog d = new BeallitasokDialog(view,
                model.getMunkaPerc(), model.getRovidSzunetPerc(), model.getHosszuSzunetPerc());

        d.getOkGomb().addActionListener(e -> {
            model.beallit(d.getMunka(), d.getRovid(), d.getHosszu());
            d.dispose();
        });
        d.getMegseGomb().addActionListener(e -> d.dispose());

        d.setVisible(true);   // modal: waits here until the dialog is closed
    }
}