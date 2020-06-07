package com.company.view;

import com.company.controller.Controller;
import com.company.model.Point;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawComponent extends JPanel {
    public List<Point> valuesLinear;
    public int fontSize;
    public Dimension firstSize;
    public Dimension size;
    public Dimension center;
    private Controller controller;
    private Graphics graph;
    private List<List<Point>> functionsData;
    private int unitSegmentCoefficient = 15;
    public DrawComponent(Controller controller) {
        valuesLinear = new ArrayList<Point>();
        int width = 600;
        int height = 500;
        size = new Dimension(width, height);
        center = new Dimension(width / 2, height / 2);
        setPreferredSize(size);
        setSize(size);
        fontSize = 8;
        firstSize = new Dimension(600, 500);
        this.controller = controller;
        functionsData = new ArrayList<>();
        functionsData.add(new ArrayList<>());
        functionsData.add(new ArrayList<>());
    }
    public void incrementUnitSegment() {
        if (unitSegmentCoefficient < 30)
            this.unitSegmentCoefficient++;
    }
    public void decrementUnitSegment() {
        if (unitSegmentCoefficient > 10)
            this.unitSegmentCoefficient--;
    }
    private void drawAxis(Graphics graph) {
        center = new Dimension(size.width / 2, size.height / 2);
        graph.drawLine(10, size.height / 2, size.width - 1, size.height / 2);
        graph.drawLine(size.width / 2, 10, size.width / 2, size.height - 1);
        graph.drawLine(size.width - 30, size.height / 2 - 10, size.width - 1, size.height / 2);
        graph.drawLine(size.width - 30, size.height / 2 + 10, size.width - 1, size.height / 2);
        graph.drawLine(size.width / 2, 10, size.width / 2 - 10, 30);
        graph.drawLine(size.width / 2, 10, size.width / 2 + 10, 30);
        graph.drawString("X", size.width - 20, size.height / 2 + 20);
        graph.drawString("Y", size.width / 2 - 20, 20);
        graph.drawString("0", size.width / 2 - 10, size.height / 2 + 10);
        int counter = 1;
        for (int index = (int) size.getWidth() / 2; index < size.width; index += unitSegmentCoefficient) {
            graph.drawLine((index), size.height / 2, (index),
                    size.height / 2 + 3);
            graph.drawString(Integer.toString(counter), index + unitSegmentCoefficient, (int) size.getHeight() / 2 - 5);
            counter++;
        }
        counter = 1;
        for (int index = (int) size.getHeight() / 2; index < size.height; index += unitSegmentCoefficient) {
            graph.drawLine(size.width / 2 - 3, index, size.width / 2, index);
            graph.drawString(Integer.toString(counter), (int) size.getWidth() / 2, index + unitSegmentCoefficient);
            counter++;
        }
        counter = -1;
        for (int index = (int) size.getWidth() / 2; index > 0; index -= unitSegmentCoefficient) {
            graph.drawLine((index), size.height / 2, (index),
                    size.height / 2 + 3);
            graph.drawString(Integer.toString(counter), index - unitSegmentCoefficient, (int) size.getHeight() / 2 - 5);
            counter--;
        }
        counter = -1;
        for (int index = (int) size.getHeight() / 2; index > 0; index -= unitSegmentCoefficient) {
            graph.drawLine(size.width / 2 - 3, index, size.width / 2, index);
            graph.drawString(Integer.toString(counter), (int) size.getWidth() / 2, index - unitSegmentCoefficient);
            counter--;
        }
    }
    public void drawFunction(List<Point> values) {
        for (int index = 1; index < values.size(); index++) {
            double tempFx = (values.get(index)).getY();
            double tempX = (values.get(index)).getX();
            double prevFx = (values.get(index - 1)).getY();
            double prevX = (values.get(index - 1)).getX();
            int newFx = (int) (unitSegmentCoefficient * tempFx);
            int newX = (int) (unitSegmentCoefficient * tempX);
            int newprevFx = (int) (unitSegmentCoefficient * prevFx);
            int newPrevX = (int) (unitSegmentCoefficient * prevX);
            graph.setColor(Color.BLUE);
            int drawPrevX = center.width + newPrevX;
            int drawPrevY = center.height - newprevFx;
            int drawX = center.width + newX;
            int drawY = center.height - newFx;
            graph.drawLine(drawPrevX, drawPrevY, drawX, drawY);
            graph.setColor(Color.DARK_GRAY);
        }
    }
    private void extendChart(int pointX, int pointY) {
        while (pointX * unitSegmentCoefficient > getSize().getWidth() / 2 || pointY * unitSegmentCoefficient > getSize().getHeight() / 2) {
            int extendRatio = 2000;
            setSize((int) getSize().getWidth() + extendRatio,
                    (int) getSize().getHeight() + extendRatio);
            this.size = getSize();
            setPreferredSize(size);
            revalidate();
        }
    }
    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        graphic.setColor(Color.DARK_GRAY);
        size = new Dimension(this.getWidth(), this.getHeight());
        Graphics2D graph = (Graphics2D) graphic;
        this.graph = graph;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setFont(new Font("Comic Sans", Font.PLAIN, fontSize));
        drawAxis(graph);
        drawFunction(valuesLinear);
        for (List<Point> function : functionsData) {
            drawFunction(function);
        }
    }
    public void clear() {
        this.size = firstSize;
        setSize(size);
        setPreferredSize(size);
        revalidate();
        functionsData.clear();
        functionsData.add(new ArrayList<>());
        functionsData.add(new ArrayList<>());
    }
    public void addValue(int id, Point point) {
        functionsData.get(id).add(point);
        extendChart((int) point.getX(), (int) point.getY());
    }
}