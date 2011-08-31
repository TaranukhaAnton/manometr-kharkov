package org.jfm.views;

import javax.swing.*;
import org.jfm.views.fview.FView;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.jfm.main.Options;
import org.jfm.event.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FileViewPanel extends JFMPanel {
  
  private JScrollPane scroll=new JScrollPane();
  private FView view=new FView();

  public void setPanelLocation(int l){
    super.setPanelLocation(l);
  }
  
  private void setViewFile(){
    int[] indexes;
    java.util.Vector files=null;
    int l=getPanelLocation();
    if(l==Options.RIGHT_PANEL){
      files=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
    }else{
      files=Options.getRightFiles();
      indexes=indexes=Options.getRightPanelSelections();      
    }    
    
    if(indexes==null || indexes.length<=0){
      return;
    }
    
    if(files==null || files.size()<=0){
      return;
    }
    
    FileElement el=(FileElement)files.elementAt(indexes[0]);
    view.setEl(el);        
    statusLabel.setText(el.getPath());    
  }

  public void requestFocus(){    
    super.requestFocus();
    view.requestFocus();
  }
  
  public FileViewPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }    
  }
  
  private void jbInit() throws Exception{
    this.setLayout(new BorderLayout());
    this.add(scroll,BorderLayout.CENTER);
    this.add(statusLabel,BorderLayout.NORTH);
    scroll.setViewportView(view);
    Broadcaster.addFileListSelectionListener(new FileListSelectionListener(){
       public void fileListSelectionChanged(FileListSelectionEvent ev){
          if(getPanelLocation()!=ev.getPanelLocation()){
            setViewFile();
          }
       }
    });

    statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));    
    view.addFocusListener(new FocusListener(){
      public void focusGained(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.foreground"));
        Options.setActivePanel(getPanelLocation());                
      }
      
      public void focusLost(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
      }
    });  
    
  }
}
