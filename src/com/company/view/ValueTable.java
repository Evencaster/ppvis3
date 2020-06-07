package com.company.view;

import com.company.model.Point;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ValueTable {
    public Vector<String> columns;
    public DefaultTableModel model;
    public JTable table;
    public JScrollPane scrollPane;
    public MainWindow mainWindow;
    public ValueTable(MainWindow mainWindow) {
        columns = new Vector<String>();
        model = new DefaultTableModel(columns, 0);
        this.mainWindow = mainWindow;
    }
    JScrollPane buildComponent() {
        columns.add("x");
        columns.add("y");
        table = new JTable();
        table.setModel(model);
        scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(150, 350));
        return scrollPane;
    }
    public void addValue(Point rowValue) {
        Vector<Double> vector = new Vector<>();
        vector.add(rowValue.getX());
        vector.add(rowValue.getY());
        model.addRow(vector);
    }
    public void clear() {
        model = new DefaultTableModel(columns, 0);
        table.setModel(model);
    }
}