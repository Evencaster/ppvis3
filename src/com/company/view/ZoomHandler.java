package com.company.view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ZoomHandler implements MouseWheelListener {
    public DrawComponent graphic;
    public InputPanel buttons;
    public MainWindow mainWindow;
    public ZoomHandler(MainWindow mainWindow, DrawComponent graphic, InputPanel buttons) {
        this.mainWindow = mainWindow;
        this.graphic = graphic;
        this.buttons = buttons;
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (event.getPreciseWheelRotation() < 0 && KeyEvent.VK_CONTROL != 0) {
            graphic.incrementUnitSegment();
            graphic.repaint();
        }
        if (event.getPreciseWheelRotation() > 0 && KeyEvent.VK_CONTROL != 0) {
            graphic.decrementUnitSegment();
            graphic.repaint();
        }
    }
}