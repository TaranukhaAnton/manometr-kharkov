package org.jfm.views;

import java.io.File;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 *
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FileElement extends File implements Comparable<File> {

    public FileElement(String pathname) {
        this(pathname, false);
    }

    public FileElement(String pathname, boolean topFile) {
        super(pathname);
        setTopFile(topFile);
    }

    public String toString() {
        if (isTopFile()) {
            return "..";
        } else {
            return this.getName();
        }
    }

    public void setTopFile(boolean topFile) {
        this.topFile = topFile;
    }

    public boolean isTopFile() {
        return topFile;
    }

    public FileElement getRootFile() {
        return getRootFile(this);
    }

    private FileElement getRootFile(FileElement f) {
        String parentPath = f.getParent();
        if (parentPath == null) return f;

        FileElement parent = new FileElement(parentPath);
        return getRootFile(parent);
    }


    private boolean topFile;

    //**Compare those two objects.*//*
    public int compareTo(File el) {
        //   if(!(o instanceof FileElement)) return -1;

        //FileElement el=(FileElement)o;

        if (this.isDirectory() && !el.isDirectory()) return -1;

        if (!this.isDirectory() && el.isDirectory()) return 1;

        return this.toString().compareTo(el.toString());
    }
}