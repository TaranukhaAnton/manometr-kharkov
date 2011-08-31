package org.jfm.views;

import java.awt.*;
import javax.swing.*;
import org.jfm.event.*;
import org.jfm.main.*;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public abstract class JFMPanel extends JPanel {

  private JFMPanel otherPanel;
  private int location=-1;
  protected javax.swing.JLabel statusLabel=new javax.swing.JLabel();

  public void setPanelLocation(int l){
    switch(l){
      case Options.LEFT_PANEL:
      case Options.RIGHT_PANEL:
        location=l;
        break;
      default:
        throw new InternalError("Location not good");
    }
  }

  public int getPanelLocation(){
    return location;
  }

  public JFMPanel() {
    init();
  }

  private void init(){
    Broadcaster.addChangePanelListener(new ChangePanelListener(){
      public void changePanel(ChangePanelEvent e){
        if(e.getLocation()==getPanelLocation())
        getOtherPanel().requestFocus();
      }
    });
  }

  public void setOtherPanel(JFMPanel p){
    otherPanel=p;
  }

  public JFMPanel getOtherPanel(){
    return otherPanel;
  }
}