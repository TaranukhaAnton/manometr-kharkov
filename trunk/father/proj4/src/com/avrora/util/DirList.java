package com.avrora.util;

import java.io.File;


public class DirList {
    public static void main(String argv[]) throws Exception {

        File userdir = new File("D:\\projects\\~FATHER\\project4\\lib");
        System.out.println("File.list()\n============");
        for (String fn : userdir.list()) {
            System.out.println("./lib/" + fn);
        }
    }
}

