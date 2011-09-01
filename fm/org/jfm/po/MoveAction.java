package org.jfm.po;

import javax.swing.Action;
import javax.swing.JOptionPane;
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

public class MoveAction implements Action {

  private ProgressActionDialog progress=null;
  private Vector movedFiles=new Vector();
  private boolean cancel=false;
  private     FileElement toDir;

  public MoveAction() {
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
    return true;
  }
  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }
  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }
 
  public void actionPerformed(ActionEvent e) {
     Vector filesToBeMoved;
     int[] indexes;
 
     
     if(Options.getActivePanel()==Options.LEFT_PANEL){
      filesToBeMoved=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
      toDir=(FileElement)Options.getRightFiles().elementAt(0);
     }else{
      filesToBeMoved=Options.getRightFiles();
      indexes=Options.getRightPanelSelections();
      toDir=(FileElement)Options.getLeftFiles().elementAt(0);
     }
         
    if(filesToBeMoved==null || filesToBeMoved.size()==0){
      return;
    }
    
    String dir=(String)JOptionPane.showInputDialog(Options.getMainFrame(),"Move selected files to:","Move files",JOptionPane.PLAIN_MESSAGE,null,null,toDir.getPath());    
    
    if(dir==null) return;
    
    toDir=new FileElement(dir);
    
    for(int i=indexes[0];i<=indexes[1];i++){
      if(i==0) continue;
      FileElement el=(FileElement)filesToBeMoved.elementAt(i);
      movedFiles.addElement(el);                 
    }
    
    progress=new ProgressActionDialog(Options.getMainFrame(),"Move",true);
    progress.setLocationRelativeTo(Options.getMainFrame());

    progress.startAction(new ActionExecuter(){
      public void start(){
        try {
          moveFiles();
        }
        catch (ActionCancelledException ex) {
          this.cancel();
        }

        ChangeDirectoryEvent ev=new ChangeDirectoryEvent();
        ev.setSource(this);  
        ev.setDirectory(toDir.getAbsolutePath());
        Broadcaster.notifyChangeDirectoryListeners(ev);
      }
      
      public void cancel(){
        cancel=true;
      }
    });
    
    
    
  }
  
  private void moveFiles() throws ActionCancelledException{
    for(int i=0;i<movedFiles.size();i++){
      FileElement fi=(FileElement)movedFiles.elementAt(i);
      if(toDir.isDirectory()){
        fi.renameTo(new File(toDir.getAbsolutePath()+File.separator+fi.getName()));
      }else{
        fi.renameTo(toDir);
      }
    }
  }
  
}
