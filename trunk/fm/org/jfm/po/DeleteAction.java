package org.jfm.po;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import org.jfm.views.FileElement;
import org.jfm.main.Options;
import org.jfm.po.copy.*;
import java.io.*;
import org.jfm.event.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class DeleteAction implements Action {

  public DeleteAction() {
  }
  private boolean enabled=true;
  public Object getValue(String key) {
    return null;
  }
  public void putValue(String key, Object value) {
  }
  public void setEnabled(boolean b) {
    enabled=b;
  }
  public boolean isEnabled() {
    return enabled;
  }
  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }
  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }
 
  public void actionPerformed(ActionEvent e) {
     int[] indexes;
     Vector filesToBeDeleted=new Vector();


     if(Options.getActivePanel()==Options.LEFT_PANEL){
      filesToBeDeleted=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
     }else{
      filesToBeDeleted=Options.getRightFiles();
      indexes=Options.getRightPanelSelections();
     }
     
     if(filesToBeDeleted==null || filesToBeDeleted.size()<=0)
      return;
     
     Vector f=new Vector();
     
     for(int i=indexes[0];i<=indexes[1];i++){
      if(i==0) continue;
      f.addElement(filesToBeDeleted.elementAt(i));
     }
     
     if(f.size()==1){
        int result=JOptionPane.showConfirmDialog(Options.getMainFrame(),"Do you want to delete "+f.elementAt(0).toString()+"?","Delete",JOptionPane.YES_NO_OPTION);
        if(result!=JOptionPane.YES_OPTION) return;
     }else{
        int result=JOptionPane.showConfirmDialog(Options.getMainFrame(),"Do you want to delete "+f.size()+" files?","Delete",JOptionPane.YES_NO_OPTION);
        if(result!=JOptionPane.YES_OPTION) return;      
     }
     
     for(int i=0;i<f.size();i++){
      FileElement fi=(FileElement)f.elementAt(i);
      deleteFile(fi);
     }     
     
      ChangeDirectoryEvent ev=new ChangeDirectoryEvent();
      ev.setSource(this);
    //  ev.setDirectory();        
      Broadcaster.notifyChangeDirectoryListeners(ev);     
  }
  
  private void deleteFile(FileElement fi){
    if(fi.isDirectory()){
      File[] list=fi.listFiles();
      for(int i=0;i<list.length;i++){
        deleteFile(new FileElement(list[i].getPath()));
      }
      fi.delete();
    }else{
      fi.delete();
    }
  }

}
