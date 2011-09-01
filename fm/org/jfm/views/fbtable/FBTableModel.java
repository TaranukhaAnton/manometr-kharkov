package org.jfm.views.fbtable;

import org.jfm.event.Broadcaster;
import org.jfm.event.ChangeDirectoryEvent;
import org.jfm.event.ChangeDirectoryListener;
import org.jfm.main.Options;
import org.jfm.views.FileElement;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.Vector;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 *
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FBTableModel extends AbstractTableModel {

    private Object[] columnNames = new Object[]{"Name", "Size", "Date Modified", "Permissions"};
    private Vector rowData = new Vector();
    private int part = -1;

    public void setPart(int p) {
        part = p;
    }

    public int getPart() {
        return part;
    }

    public FBTableModel(int p) {
        setPart(p);
        initTable();
        addListeners();
    }

    private void addListeners() {
        Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener() {
            public void changeDirectory(ChangeDirectoryEvent e) {
                if (!(e.getSource() instanceof FBTableModel)) {
                    browseDirectory((FileElement) ((Vector) rowData.elementAt(0)).elementAt(0));
                }
            }
        });
    }

    private void initTable() {
        String currentDirectory = Options.getStartDirectory();
        browseDirectory(new FileElement(currentDirectory, true));
        //DefaultTableModel m;
        //m.addColumn();
    }

    public int getColumnIndex(Class colClass) {
        if (colClass == null) return -1;

        for (int i = 0; i < columnNames.length; i++) {
            if (this.getColumnClass(i) == colClass) return i;
        }

        return -1;
    }

    public Vector getCurrentFiles() {
        Vector files = new Vector();
        for (int i = 0; i < rowData.size(); i++) {
            files.addElement(((Vector) rowData.elementAt(i)).elementAt(0));
        }

        return files;
    }

    public void browseDirectory(final FileElement el) {
        this.clear(); //removing the old rows
        final java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM/dd/yyyy");
        final Vector allFiles = new Vector();

        Vector firstRow = new Vector();
        el.setTopFile(true);
        firstRow.addElement(el);
        firstRow.addElement("up dir");
        firstRow.addElement(format.format(new java.util.Date(el.lastModified())));
        firstRow.addElement("");

        addRow(firstRow);
        allFiles.addElement(el);

        ChangeDirectoryEvent dirEvent = new ChangeDirectoryEvent(el.getPath());
        dirEvent.setSource(this);
        Broadcaster.notifyChangeDirectoryListeners(dirEvent);

        File[] files = el.listFiles();
        if (files == null) return;
        FileElement[] elements = new FileElement[files.length];

        for (int i = 0; i < files.length; i++) {
            elements[i] = new FileElement(files[i].getPath());
        }
        java.util.Arrays.sort(elements);

        for (int i = 0; i < elements.length; i++) {
            Vector v = new Vector();
            v.addElement(elements[i]);

            if (elements[i].isDirectory()) {
                v.addElement("dir");
            } else {
                v.addElement(String.valueOf(elements[i].length()) + " bytes");
            }

            v.addElement(format.format(new java.util.Date(elements[i].lastModified())));
            v.addElement(getPermissions(elements[i]));
            addRow(v);
            allFiles.addElement(elements[i]);
        }

        if (getPart() == Options.LEFT_PANEL) {
            Options.setLeftFiles(allFiles);
        } else {
            Options.setRightFiles(allFiles);
        }
    }

    private String getPermissions(File f) {
        String result = "";
        result += (f.canRead()) ? "r" : "-";
        result += (f.canWrite()) ? "w" : "-";
        result += (f.canExecute()) ? "x " : "-";
        return result;

    }


    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Removes all rows from the data model.
     */
    public void clear() {
        int oldSize = rowData.size();
        rowData.clear();
        this.fireTableRowsDeleted(0, oldSize);
    }

    /**
     * Add a row to the data model.
     */
    public void addRow(Vector newRow) {
        rowData.add(newRow);
        this.fireTableRowsInserted(rowData.size(), rowData.size());
    }

/*    *//** Add a row to the data model. *//*
    public void addRow(Object[] newRow) {
      addRow(convertToVector(newRow));
    }*/


    /**
     * Returns the column name
     */
    public String getColumnName(int column) {
        return columnNames[column].toString();
    }

    /**
     * Returns the number of rows
     */
    public int getRowCount() {
        return this.rowData.size();
    }

    /**
     * Returns the number of columns
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the element at the specified row and column
     */
    public Object getValueAt(int row, int column) {
      //  if (column > 2) return "as";
        return ((Vector) rowData.elementAt(row)).elementAt(column);

    }

    /**
     * Returns whether the cell is editable. It isn't.
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Sets an object at the specified row and column
     */
    public void setValueAt(Object value, int row, int column) {
        if (row > 2) return;
        ((Vector) rowData.elementAt(row)).setElementAt(value, column);
        fireTableCellUpdated(row, column);
    }

    /**
     * Returns a vector that contains the same objects as the array.
     *
     * @@param anArray  the array to be converted
     * @@return the new vector; if <code>anArray</code> is <code>null</code>,
     * returns <code>null</code>
     */
    protected static Vector convertToVector(Object[] anArray) {
        if (anArray == null)
            return null;

        Vector v = new Vector(anArray.length);
        for (int i = 0; i < anArray.length; i++) {
            v.addElement(anArray[i]);
        }
        return v;
    }

    /**
     * Returns a vector of vectors that contains the same objects as the array.
     *
     * @@param anArray  the double array to be converted
     * @@return the new vector of vectors; if <code>anArray</code> is
     * <code>null</code>, returns <code>null</code>
     */
    protected static Vector convertToVector(Object[][] anArray) {
        if (anArray == null)
            return null;

        Vector v = new Vector(anArray.length);
        for (int i = 0; i < anArray.length; i++) {
            v.addElement(convertToVector(anArray[i]));
        }
        return v;
    }
}