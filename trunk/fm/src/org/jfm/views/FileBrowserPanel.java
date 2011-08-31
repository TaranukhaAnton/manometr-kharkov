package org.jfm.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.jfm.views.fbtable.*;
import org.jfm.event.*;
import org.jfm.main.Options;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FileBrowserPanel extends JFMPanel {
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane scroll = new JScrollPane();
  private FBTableModel model;   
  private FBTable table = new FBTable();
  private int selectedRow=-1;  
  private JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  private JComboBox rootsCombo=new JComboBox();

  public void setPanelLocation(int l){
    super.setPanelLocation(l);
    model=new FBTableModel(l);
    table.setModel(model);  
    ((FBTableModel)table.getModel()).setPart(l);
  }
    
  public FileBrowserPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void requestFocus(){
    
    super.requestFocus();
    table.requestFocus();
  }
  
    private void setupRootsCombo(){
     java.io.File[] roots=java.io.File.listRoots();
     if(roots!=null){
        for(int i=0;i<roots.length;i++){
        //System.err.println(roots[i].getPath());     
          rootsCombo.addItem(roots[i].getPath());
          if(new FileElement(Options.getStartDirectory()).getRootFile().equals(roots[i])){
            rootsCombo.setSelectedIndex(i);
          }
        }
     }
     
     
     rootsCombo.addItemListener(new ItemListener(){
        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange()==e.DESELECTED){
                return;
            }
            model.browseDirectory(new FileElement(rootsCombo.getSelectedItem().toString()));            
        }
     });
     
 //    rootsCombo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt "+(this.getPanelLocation()==Options.LEFT_PANEL?"F1":"F2")),"showRootsCombo");
   //  rootsCombo.getActionMap().put("showRootsCombo",new ShowRootsComboAction());
                
  }
  
  private void jbInit() throws Exception {
    
    this.setLayout(borderLayout1);    
    this.add(scroll,BorderLayout.CENTER);
    this.add(topPanel,BorderLayout.NORTH);
    topPanel.add(rootsCombo);
    topPanel.add(statusLabel);
    setupRootsCombo();
    scroll.setViewportView(table);
    
    scroll.getViewport().setBackground(UIManager.getColor("window"));
    
    
    Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
      public void changeDirectory(ChangeDirectoryEvent e){
        if(e.getSource().equals(table.getModel())){
          statusLabel.setText(e.getDirectory());
        }
      }
    });
      
    //Options.setActivePanel(getPanelLocation()); 
    statusLabel.setText(Options.getStartDirectory());
    statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    
    table.addFocusListener(new FocusListener(){
      public void focusGained(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.foreground"));
        try {
          table.getSelectionModel().setSelectionInterval(selectedRow,selectedRow);
        }
        catch (Exception ignored) { }
        Options.setActivePanel(getPanelLocation());                
      }
      
      public void focusLost(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
        selectedRow=table.getSelectedRow();
       // table.getSelectionModel().clearSelection();        
      }
    });  
    
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        if(e.getValueIsAdjusting()) return;
        if(table.getSelectionModel().isSelectionEmpty()) return;
                
        int[] selections=new int[2];
        int firstIndex = table.getSelectionModel().getMinSelectionIndex();
        int lastIndex = table.getSelectionModel().getMaxSelectionIndex();
        
        selections[0]=firstIndex;
        selections[1]=lastIndex;
        if(FileBrowserPanel.this.getPanelLocation()==Options.LEFT_PANEL)
          Options.setLeftPanelSelections(selections);
        else
          Options.setRightPanelSelections(selections);
      }
    });    
    
    table.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent event){
          if(SwingUtilities.isRightMouseButton(event)){
            if(table.rowAtPoint(event.getPoint())<0){
              return;
            }            
            Options.getPopupMenu().show(table,event.getX(),event.getY());
          }
        }
    });
  }
}
