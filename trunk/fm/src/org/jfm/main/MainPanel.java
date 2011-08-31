package org.jfm.main;

import java.awt.*;
import java.awt.event.*;
import org.jfm.po.*;
import org.jfm.views.*;
import org.jfm.event.*;
import javax.swing.*;
import java.util.Vector;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class MainPanel extends JPanel {
  private BorderLayout borderLayout1 = new BorderLayout();

  private ButtonsPanel btPanel=new ButtonsPanel();
  private JSplitPane split = new JSplitPane();

  public MainPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    removeSplitKeyBindings();
    this.setLayout(borderLayout1);

    this.add(btPanel,BorderLayout.SOUTH);
    this.add(split,  BorderLayout.CENTER);

    JFMPanel leftPanel=ViewportOptions.getLeftViewComponent(ViewportOptions.FILE_BROWSER);
    JFMPanel rightPanel=ViewportOptions.getRightViewComponent(ViewportOptions.FILE_BROWSER);
    leftPanel.setPanelLocation(Options.LEFT_PANEL);
    rightPanel.setPanelLocation(Options.RIGHT_PANEL);

    leftPanel.setOtherPanel(rightPanel);
    rightPanel.setOtherPanel(leftPanel);
    leftPanel.requestFocus();
    split.add(leftPanel, JSplitPane.LEFT);
    split.add(rightPanel, JSplitPane.RIGHT);
    split.setDividerLocation(350);

    setButtonsPanel();
    Broadcaster.addChangeViewListener(new ChangeViewListener(){
       public void viewChanged(ChangeViewEvent ev){
           int view=ev.getNewView();
           switch(view){  
              case ViewportOptions.FILE_BROWSER:
                   if(Options.getActivePanel()==Options.LEFT_PANEL && ViewportOptions.getCurrentLeftViewType()!=ViewportOptions.FILE_BROWSER){
                     JFMPanel panel=ViewportOptions.getLeftViewComponent(ViewportOptions.FILE_BROWSER);
                     panel.setPanelLocation(Options.LEFT_PANEL);
                     split.setLeftComponent(panel);
                     break;
                   } 
                   if(Options.getActivePanel()==Options.RIGHT_PANEL && ViewportOptions.getCurrentRightViewType()!=ViewportOptions.FILE_BROWSER){
                     JFMPanel panel=ViewportOptions.getRightViewComponent(ViewportOptions.FILE_BROWSER);
                     panel.setPanelLocation(Options.RIGHT_PANEL);
                     split.setRightComponent(panel);
                     break;
                   }                    
              break;
              case ViewportOptions.TREE_BROWSER:
                   if(Options.getActivePanel()==Options.LEFT_PANEL && ViewportOptions.getCurrentLeftViewType()!=ViewportOptions.TREE_BROWSER){
                     JFMPanel panel=ViewportOptions.getLeftViewComponent(ViewportOptions.TREE_BROWSER);
                     panel.setPanelLocation(Options.LEFT_PANEL);
                     split.setLeftComponent(panel);
                     break;
                   } 
                   if(Options.getActivePanel()==Options.RIGHT_PANEL && ViewportOptions.getCurrentRightViewType()!=ViewportOptions.TREE_BROWSER){
                     JFMPanel panel=ViewportOptions.getRightViewComponent(ViewportOptions.TREE_BROWSER);
                     panel.setPanelLocation(Options.RIGHT_PANEL);
                     split.setRightComponent(panel);
                     break;
                   }                    
              break;
              case ViewportOptions.FILE_VIEWER:
                   if(Options.getActivePanel()==Options.LEFT_PANEL && ViewportOptions.getCurrentLeftViewType()!=ViewportOptions.FILE_VIEWER){
                     JFMPanel panel=ViewportOptions.getLeftViewComponent(ViewportOptions.FILE_VIEWER);
                     panel.setPanelLocation(Options.LEFT_PANEL);
                     split.setLeftComponent(panel);
                     break;
                   } 
                   if(Options.getActivePanel()==Options.RIGHT_PANEL && ViewportOptions.getCurrentRightViewType()!=ViewportOptions.FILE_VIEWER){
                     JFMPanel panel=ViewportOptions.getRightViewComponent(ViewportOptions.FILE_VIEWER);
                     panel.setPanelLocation(Options.RIGHT_PANEL);
                     split.setRightComponent(panel);
                     break;
                   }                                  
              break;              
           }    
       }
    });
  }

  private void removeSplitKeyBindings(){
    ActionMap newSplitActionMap=new ActionMap();
    split.setActionMap(newSplitActionMap);
  }

  private void setButtonsPanel(){
    Vector buttons=new Vector();

    JButton f1Button=new JButton("Help (F1)");
    HelpAction help=new HelpAction();
    f1Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"),"helpButton");
    f1Button.getActionMap().put("helpButton",help);
    f1Button.addActionListener(help);

//    JButton f2Button=new JButton("Menu (F2)");
//    PanelMenuAction menu=new PanelMenuAction();
//    f2Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"),"menuButton");
//    f2Button.getActionMap().put("menuButton",menu);
//    f2Button.addActionListener(menu);

    JButton f3Button=new JButton("View (F3)");
    ViewFileAction view=new ViewFileAction();
    f3Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F3"),"viewButton");
    f3Button.getActionMap().put("viewButton",view);
    f3Button.addActionListener(view);

    JButton f4Button=new JButton("Edit (F4)");
    EditFileAction edit=new EditFileAction();
    f4Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"),"editButton");
    f4Button.getActionMap().put("editButton",edit);
    f4Button.addActionListener(edit);

    JButton f5Button=new JButton("Copy (F5)");
    CopyAction copy=new CopyAction();
    f5Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"),"copyButton");
    f5Button.getActionMap().put("copyButton",copy);
    f5Button.addActionListener(copy);

    JButton f6Button=new JButton("Move (F6)");
    MoveAction move=new MoveAction();
    f6Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F6"),"moveButton");
    f6Button.getActionMap().put("moveButton",move);
    f6Button.addActionListener(move);

    JButton f7Button=new JButton("Mkdir (F7)");
    MkdirAction mkdir=new MkdirAction();
    f7Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F7"),"mkdirButton");
    f7Button.getActionMap().put("mkdirButton",mkdir);
    f7Button.addActionListener(mkdir);

    JButton f8Button=new JButton("Delete (F8)");
    DeleteAction del=new DeleteAction();
    f8Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"),"delButton");
    f8Button.getActionMap().put("delButton",del);
    f8Button.addActionListener(del);

    JButton f10Button=new JButton("Quit (F10)");
    QuitAction quit=new QuitAction();
    f10Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F10"),"quitButton");
    f10Button.getActionMap().put("quitButton",quit);
    f10Button.addActionListener(quit);

    buttons.addElement(f1Button);
//    buttons.addElement(f2Button);
    buttons.addElement(f3Button);
    buttons.addElement(f4Button);
    buttons.addElement(f5Button);
    buttons.addElement(f6Button);
    buttons.addElement(f7Button);
    buttons.addElement(f8Button);
    buttons.addElement(f10Button);

    btPanel.setButtons(buttons);
  }
}
