package com.company.model;

import com.company.view.MainWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class LinearFunction implements Runnable {
    public double begin;
    public double end;
    private Lock lock;
    public static final int FUNCTION_ID = 0;
    private List<Point> data;
    private double step;
    private MainWindow mainWindow;
    public LinearFunction(Lock lock, MainWindow mainWindow) {
        this.lock = lock;
        begin = 0;
        end = 50;
        this.data = new ArrayList<>();
        this.step = 0.1;
        this.mainWindow = mainWindow;
    }
    public double function(double x) {
        return 3 * x + 5;
    }
    @Override
    public void run() {
        double beginX = begin;
        double endX = end;
        double tempFx = 0;
        for (double x = beginX; x <= endX; x += step) {
            tempFx = function(x);
            tempFx = Math.round(tempFx * 10d) / 10d;
            x = Math.round(x * 10d) / 10d;
            Point secondPoint = new Point(x, tempFx);
            data.add(secondPoint);
            lock.lock();
            try {
                mainWindow.getGraphic().addValue(FUNCTION_ID, secondPoint);
                mainWindow.getGraphic().repaint();
            } finally {
                lock.unlock();
            }
        }
    }
}