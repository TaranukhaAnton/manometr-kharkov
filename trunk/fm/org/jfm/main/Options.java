package org.jfm.main;

import java.util.Vector;
import org.jfm.event.*;
import javax.swing.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class Options { 
  public static final int LEFT_PANEL=1;
  public static final int RIGHT_PANEL=2;

  private static String startDirectory=null;
  private static int[] leftSelections=new int[]{-1,-1};
  private static int[] rightSelections=new int[]{-1,-1};
  private static int activePanel;
  private static Vector leftFiles;
  private static Vector rightFiles;
  private static MainFrame frame;
  
  private static JPopupMenu opMenu=new JPopupMenu();
//  private static JMenuItem copyMenu=new JMenuItem("Copy");
//  private static JMenuItem moveMenu=new JMenuItem("Move");
//  private static JMenuItem deleteMenu=new JMenuItem("Delete");
//  private static JMenuItem viewMenu=new JMenuItem("View");
//  private static JMenuItem editMenu=new JMenuItem("Edit");
//  private static JMenuItem propsMenu=new JMenuItem("Properties");
  
//  static{
//    setUpMenuStuff();
//  }
//
//  private static void setUpMenuStuff(){
//    opMenu.add(copyMenu);
//    opMenu.add(moveMenu);
//    opMenu.add(deleteMenu);
//    opMenu.addSeparator();
//    opMenu.add(viewMenu);
//    opMenu.add(editMenu);
//    opMenu.addSeparator();
//    opMenu.add(propsMenu);
//
//    copyMenu.addActionListener(new org.jfm.po.CopyAction());
//    moveMenu.addActionListener(new org.jfm.po.MoveAction());
//    deleteMenu.addActionListener(new org.jfm.po.DeleteAction());
//    viewMenu.addActionListener(new org.jfm.po.ViewFileAction());
//    editMenu.addActionListener(new org.jfm.po.EditFileAction());
//    propsMenu.addActionListener(new org.jfm.po.FilePropertiesAction());
//  }
  
  public static JPopupMenu getPopupMenu(){
    return opMenu;
  }
  
  public static void setMainFrame(MainFrame f){
    frame=f;
  }
  
  public static MainFrame getMainFrame(){
    return frame;
  }
  
  public static void setRightFiles(Vector v){
    rightFiles=v;
  }
  
  public static Vector getRightFiles(){
    return rightFiles; 
  } 

  public static void setLeftFiles(Vector v){
    leftFiles=v;
  }
  
  public static Vector getLeftFiles(){
    return leftFiles; 
  } 
  
  public static void setStartDirectory(String path){
    startDirectory=path;
  }
  
  public static String getStartDirectory(){
    if(startDirectory==null){
      setStartDirectory(System.getProperty("user.dir"));
    }    
    return startDirectory;
  }

  public static void setRightPanelSelections(int[] sel){
    rightSelections=sel;
    FileListSelectionEvent ev=new FileListSelectionEvent();
    ev.setSource(null);
    ev.setFilesIndexes(sel);
    ev.setPanelLocation(RIGHT_PANEL);
    Broadcaster.notifyFileListSelectionListener(ev); 
  }
  
  public static int[] getRightPanelSelections(){
    return rightSelections;
  }
  
  public static void setLeftPanelSelections(int[] sel){
    leftSelections=sel;
    FileListSelectionEvent ev=new FileListSelectionEvent();
    ev.setSource(null);
    ev.setFilesIndexes(sel);
    ev.setPanelLocation(LEFT_PANEL);
    Broadcaster.notifyFileListSelectionListener(ev); 
  }
  
  public static int[] getLeftPanelSelections(){
    return leftSelections;
  }
  
  public static void setActivePanel(int p){
    if(p!=LEFT_PANEL && p!=RIGHT_PANEL) throw new InternalError("Bad active panel");    
    activePanel=p;
  }
  
  public static int getActivePanel(){
    return activePanel;
  }  
  
}