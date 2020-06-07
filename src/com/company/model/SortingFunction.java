package com.company.model;

import com.company.view.MainWindow;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class SortingFunction implements Runnable {
    public static final int FUNCTION_ID = 1;
    private static final int STRAP_DIGIT = 10;
    private int n;
    private int k;
    private Lock lock;
    private MainWindow frame;
    private int sleepTime;
    private int peakLimit;
    public SortingFunction(int n, int k, Lock lock, MainWindow frame) {
        this.n = n;
        this.k = k;
        this.lock = lock;
        this.frame = frame;
        sleepTime = 500;
        peakLimit = 2000;
    }
    @Override
    public void run() {
        for (int currentSize = 2; currentSize < n; currentSize++) {
            int commonTime = 0;
            for (int currentArrayCount = 1; currentArrayCount < k; currentArrayCount++) {
                commonTime += sortTime(generateRandomArray(currentSize));
            }
            int averageTime = commonTime / k;
            lock.lock();
            try {
                Point point = new Point(currentSize, averageTime);
                frame.getGraphic().addValue(FUNCTION_ID, point);
                frame.getMainPointsTable().addValue(point);
                frame.getGraphic().repaint();
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                currentSize = n;
            }
        }
    }
    private static void mergeSort(int[] source, int left, int right) {
        int delimiter = left + ((right - left) / 2) + 1;
        if (delimiter > 0 && right > (left + 1)) {
            mergeSort(source, left, delimiter - 1);
            mergeSort(source, delimiter, right);
        }
        int[] buffer = new int[right - left + 1];
        int cursor = left;
        for (int i = 0; i < buffer.length; i++) {
            if (delimiter > right || source[cursor] > source[delimiter]) {
                buffer[i] = source[cursor];
                cursor++;
            } else {
                buffer[i] = source[delimiter];
                delimiter++;
            }
        }
        System.arraycopy(buffer, 0, source, left, buffer.length);
    }
    private long sortTime(int[] arrayToSort) {
        long startTime = System.nanoTime() / STRAP_DIGIT;
        mergeSort(arrayToSort, 0, arrayToSort.length - 1);
        long endTime = System.nanoTime() / STRAP_DIGIT;
        long result = endTime - startTime;
        if (result > peakLimit) {
            result = sortTime(arrayToSort);
        }
        return result;
    }
    private int[] generateRandomArray(int currentArraySize) {
        int[] result = new int[currentArraySize];
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(currentArraySize);
        }
        return result;
    }
}