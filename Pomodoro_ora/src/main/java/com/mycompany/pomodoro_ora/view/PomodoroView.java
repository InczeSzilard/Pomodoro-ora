package com.mycompany.pomodoro_ora.view;

import com.mycompany.pomodoro_ora.model.Allapot;
import com.mycompany.pomodoro_ora.model.PomodoroFigyelo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// The window. It only shows things, it doesn't decide anything.
// It's an observer: the model tells it when something changes.
public class PomodoroView extends JFrame implements PomodoroFigyelo {

    private final JLabel idoFelirat = new JLabel("25:00", SwingConstants.CENTER);
    private final JLabel allapotFelirat = new JLabel("Szuneteltetve", SwingConstants.CENTER);

    private final JButton startGomb = new JButton("Start");
    private final JButton szunetGomb = new JButton("Szunet");
    private final JButton resetGomb = new JButton("Reset");
    private final JButton beallitasGomb = new JButton("Beallitasok");

    private final JPanel kozep = new JPanel(new BorderLayout());

    // Background color for each state. A new state only needs one more line here.
    private final Map<Allapot, Color> szinek = new EnumMap<>(Allapot.class);

    public PomodoroView() {
        super("Pomodoro");

        szinek.put(Allapot.MUNKA, new Color(230, 90, 80));           // red
        szinek.put(Allapot.ROVID_SZUNET, new Color(90, 190, 120));   // green
        szinek.put(Allapot.HOSSZU_SZUNET, new Color(80, 140, 220));  // blue
        szinek.put(Allapot.SZUNETEL, Color.LIGHT_GRAY);

        idoFelirat.setFont(new Font("SansSerif", Font.BOLD, 72));
        allapotFelirat.setFont(new Font("SansSerif", Font.PLAIN, 24));

        kozep.add(idoFelirat, BorderLayout.CENTER);
        kozep.add(allapotFelirat, BorderLayout.SOUTH);

        JPanel gombok = new JPanel(new FlowLayout());
        gombok.add(startGomb);
        gombok.add(szunetGomb);
        gombok.add(resetGomb);
        gombok.add(beallitasGomb);

        add(kozep, BorderLayout.CENTER);
        add(gombok, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        setLocationRelativeTo(null);   // middle of the screen
    }

    // ---------- observer methods ----------

    @Override
    public void tikk(int hatralevoMasodperc) {
        int perc = hatralevoMasodperc / 60;
        int mp = hatralevoMasodperc % 60;
        idoFelirat.setText(String.format("%02d:%02d", perc, mp));   // MM:SS
    }

    @Override
    public void allapotValtozott(Allapot uj) {
        // no if/else on the state: the text comes from the enum, the color from the map
        allapotFelirat.setText(uj.getFelirat());
        kozep.setBackground(szinek.get(uj));
    }

    // ---------- the controller needs the buttons ----------

    public JButton getStartGomb() {
        return startGomb;
    }

    public JButton getSzunetGomb() {
        return szunetGomb;
    }

    public JButton getResetGomb() {
        return resetGomb;
    }

    public JButton getBeallitasGomb() {
        return beallitasGomb;
    }
}