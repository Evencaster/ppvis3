package com.company.view;

import com.company.controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindow {
    public JFrame frame;
    public Controller controller;
    private InputPanel inputPanel;
    private ValueTable mainPointsTable;
    private DrawComponent graphic;
    public JScrollPane scroll;
    public DrawComponent getGraphic() {
        return graphic;
    }
    public ValueTable getMainPointsTable() {
        return mainPointsTable;
    }
    public MainWindow() {
        ReentrantLock lock = new ReentrantLock();
        controller = new Controller(MainWindow.this, lock);
        frame = new JFrame();
        inputPanel = new InputPanel();
    }
    public JFrame buildFrame() {
        frame.setTitle("Function Drawing");
        int width = 900;
        int height = 700;
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout());
        graphic = new DrawComponent(controller);
        mainPointsTable = new ValueTable(this);
        scroll = new JScrollPane(graphic);
        scroll.setPreferredSize(new Dimension(605, 505));
        scroll.setAutoscrolls(true);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        frame.add(mainPointsTable.buildComponent(), BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(inputPanel.buildComponent(), BorderLayout.EAST);
        HoldAndDragHanlder listener = new HoldAndDragHanlder(graphic);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scroll.getViewport().addMouseListener(listener);
        scroll.getViewport().addMouseMotionListener(listener);
        ZoomHandler zoomListener = new ZoomHandler(MainWindow.this, graphic, inputPanel);
        scroll.addMouseWheelListener(zoomListener);
        inputPanel.getMainButton().addActionListener(event -> {
            controller.stopThreads();
            mainPointsTable.clear();
            graphic.clear();
            startCalculation();
        });
        inputPanel.getButtonStop().addActionListener(event -> {
            controller.stopThreads();
            mainPointsTable.clear();
            graphic.clear();
        });
        return frame;
    }
    public void startCalculation() {
        controller.startLinearFunctionThread();
        controller.startSortFunctionThread(inputPanel.getN(), inputPanel.getK());
    }
}