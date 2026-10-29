package com.mycompany.pomodoro_ora.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

// Settings window. Modal: while it's open, the main window can't be used.
public class BeallitasokDialog extends JDialog {

    private final JSpinner munka;
    private final JSpinner rovid;
    private final JSpinner hosszu;

    private final JButton okGomb = new JButton("OK");
    private final JButton megseGomb = new JButton("Megse");

    public BeallitasokDialog(JFrame szulo, int munkaPerc, int rovidPerc, int hosszuPerc) {
        super(szulo, "Beallitasok", true);   // true = modal

        // number fields from 1 to 120 minutes, starting with the current values
        munka = new JSpinner(new SpinnerNumberModel(munkaPerc, 1, 120, 1));
        rovid = new JSpinner(new SpinnerNumberModel(rovidPerc, 1, 120, 1));
        hosszu = new JSpinner(new SpinnerNumberModel(hosszuPerc, 1, 120, 1));

        setLayout(new GridLayout(4, 2, 8, 8));
        add(new JLabel("Munka (perc):"));
        add(munka);
        add(new JLabel("Rovid szunet (perc):"));
        add(rovid);
        add(new JLabel("Hosszu szunet (perc):"));
        add(hosszu);
        add(okGomb);
        add(megseGomb);

        pack();
        setLocationRelativeTo(szulo);
    }

    public int getMunka() {
        return (Integer) munka.getValue();
    }

    public int getRovid() {
        return (Integer) rovid.getValue();
    }

    public int getHosszu() {
        return (Integer) hosszu.getValue();
    }

    public JButton getOkGomb() {
        return okGomb;
    }

    public JButton getMegseGomb() {
        return megseGomb;
    }
}