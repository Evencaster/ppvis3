package com.company.controller;

import com.company.model.LinearFunction;
import com.company.model.SortingFunction;
import com.company.view.MainWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Controller {
    public MainWindow window;
    private LinearFunction linearFunction;
    private SortingFunction sortingFunction;
    private Lock lock;
    private List<Thread> threads;
    public Controller(MainWindow window, Lock lock) {
        this.window = window;
        this.lock = lock;
        this.linearFunction = new LinearFunction(lock, window);
        this.sortingFunction = new SortingFunction(1, 2, lock, window);
        this.threads = new ArrayList<>();
    }
    public void startLinearFunctionThread() {
        this.linearFunction = new LinearFunction(lock, window);
        Thread LinearThread = new Thread(linearFunction);
        threads.add(LinearThread);
        LinearThread.start();
    }
    public void startSortFunctionThread(int n, int k) {
        this.sortingFunction = new SortingFunction(n, k, lock, window);
        Thread sortThread = new Thread(sortingFunction);
        threads.add(sortThread);
        sortThread.start();
    }
    public void stopThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }
}