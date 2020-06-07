package com.company.view;

import javax.swing.*;
import java.awt.*;

public class InputPanel {
    public JPanel panel;
    public JTextField nValue;
    public JTextField kValue;
    public JButton buttonStart;
    JButton buttonStop;
    public InputPanel() {
        panel = new JPanel();
        nValue = new JTextField(3);
        kValue = new JTextField(3);
        buttonStart = new JButton("Рисовать");
        buttonStop = new JButton("Остановить и очистить");
    }
    JPanel buildComponent() {
        panel.setPreferredSize(new Dimension(350, 400));
        panel.setOpaque(true);
        panel.add(nValue);
        panel.add(kValue);
        panel.add(buttonStart);
        panel.add(buttonStop);
        panel.setVisible(true);
        nValue.setText("100");
        kValue.setText("10000");
        return panel;
    }
    int getN() {
        String stringValue = nValue.getText();
        if (!stringValue.equals("")) {
            return Integer.parseInt(stringValue);
        } else {
            return 5;
        }
    }
    int getK() {
        String stringValue = kValue.getText();
        if (!stringValue.equals("")) {
            return Integer.parseInt(stringValue);
        } else {
            return 10000;
        }
    }
    public JButton getMainButton() {
        return buttonStart;
    }
    JButton getButtonStop() {
        return buttonStop;
    }
}