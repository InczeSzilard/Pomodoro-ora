package com.mycompany.pomodoro_ora;

import com.mycompany.pomodoro_ora.controller.PomodoroController;
import com.mycompany.pomodoro_ora.model.PomodoroModel;
import com.mycompany.pomodoro_ora.view.PomodoroView;
import javax.swing.SwingUtilities;

public class Pomodoro_ora {

    public static void main(String[] args) {
        // Swing must be started on its own thread (EDT).
        SwingUtilities.invokeLater(() -> {
            PomodoroModel model = new PomodoroModel();
            PomodoroView view = new PomodoroView();
            new PomodoroController(model, view);
            view.setVisible(true);
        });
    }
}