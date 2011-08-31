package org.jfm.po;

import javax.swing.Action;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import org.jfm.main.Options;
import org.jfm.views.FileElement;
import org.jfm.event.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class MkdirAction implements Action {

  public MkdirAction() {
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
     FileElement file;
     
     if(Options.getActivePanel()==Options.LEFT_PANEL){
      if(Options.getLeftFiles()==null || Options.getLeftFiles().size()<=0) return;
      file=(FileElement)Options.getLeftFiles().elementAt(0);
     }else{
      if(Options.getRightFiles()==null || Options.getRightFiles().size()<=0) return;
      file=(FileElement)Options.getRightFiles().elementAt(0);
     }
    
    String newDir=JOptionPane.showInputDialog(Options.getMainFrame(),"Enter the name of the new directory:","New directory",JOptionPane.PLAIN_MESSAGE);
    if(newDir==null) return;
    if(!new FileElement(file.getPath()+FileElement.separator+newDir).mkdir()){
      JOptionPane.showMessageDialog(Options.getMainFrame(),"Coudn't create directory "+ file.getPath()+FileElement.separator+newDir,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    ChangeDirectoryEvent ev=new ChangeDirectoryEvent();
    ev.setSource(this);  
    Broadcaster.notifyChangeDirectoryListeners(ev);

  }

}
