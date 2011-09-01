package org.jfm.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import org.jfm.views.ftree.*;
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

public class TreeViewPanel extends JFMPanel {
  
  private JScrollPane scroll=new JScrollPane();
  private JTree tree=new JTree();
  private FTreeModel model= null;
  private DefaultMutableTreeNode root=new DefaultMutableTreeNode("The current root");
  private int selectedRow=-1;
  private JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  private JComboBox rootsCombo=new JComboBox();
      
  public void setPanelLocation(int l){
    super.setPanelLocation(l);
    model=new FTreeModel(l,root);
    tree.setModel(model);
    statusLabel.setText(root.getUserObject().toString());      
  }
  
  public TreeViewPanel() {
    jbInit();
  }
  
  public void requestFocus(){
    
    super.requestFocus();
    tree.requestFocus();    
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
            model.initTree(rootsCombo.getSelectedItem().toString());
            statusLabel.setText(rootsCombo.getSelectedItem().toString());            
        }
     });
  }
 
  private void jbInit(){    
    Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
      public void changeDirectory(ChangeDirectoryEvent e){
        if(e.getSource().equals(tree)){
          statusLabel.setText(e.getDirectory());
        }
      }
    });    
    
    this.setLayout(new BorderLayout());
    this.add(scroll,BorderLayout.CENTER);
    this.add(topPanel,BorderLayout.NORTH);
    topPanel.add(rootsCombo);
    topPanel.add(statusLabel);
    setupRootsCombo();

    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    scroll.setViewportView(tree);
    
    scroll.getViewport().setBackground(UIManager.getColor("window"));

    statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    
    tree.addFocusListener(new FocusListener(){
      public void focusGained(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.foreground"));
        
        try {
          tree.getSelectionModel().setSelectionPath(tree.getPathForRow(selectedRow));
        }
        catch (Exception ignored) { }
        Options.setActivePanel(getPanelLocation());                
      }
      
      public void focusLost(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
        if(tree.getSelectionRows()!=null){
          selectedRow=tree.getSelectionRows()[0];
        }
        tree.getSelectionModel().clearSelection();
        
      }
    });  
    
    tree.addTreeExpansionListener(new TreeExpansionListener() {
      public void treeCollapsed(TreeExpansionEvent e) {
        
      }
    
      public void treeExpanded(TreeExpansionEvent e) {
        model.fillTreeFromNode((DefaultMutableTreeNode)e.getPath().getLastPathComponent());
      }
    }); 
    
    tree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        //if(!e.isAddedPath()) return;
        
        DefaultMutableTreeNode node=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();        
        java.util.Vector allFiles=new java.util.Vector();
        
        if(parent==null) {
          parent=node;
        }else{
          allFiles.addElement(parent.getUserObject());
        }
        
        
        
        for(int i=0;i<parent.getChildCount();i++){
          allFiles.addElement(((DefaultMutableTreeNode)parent.getChildAt(i)).getUserObject());
        }
        
        if(getPanelLocation()==Options.LEFT_PANEL){
          Options.setLeftFiles(allFiles);
          Options.setLeftPanelSelections(new int[]{parent.getIndex(node)+1,parent.getIndex(node)+1});
        }else{
          Options.setRightFiles(allFiles);
          Options.setRightPanelSelections(new int[]{parent.getIndex(node)+1,parent.getIndex(node)+1});
        }    
       // System.err.println(" allfiles:"+allFiles+" selections are:"+Options.getRightPanelSelections()[0]+","+Options.getRightPanelSelections()[1]);
      }
    });
    
    InputMap mainMap=tree.getInputMap(this.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    mainMap.put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    tree.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    tree.getInputMap().put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    putActionMapAction(tree.getActionMap());
     
  }
  
  private void putActionMapAction(ActionMap a){
    a.put("changePanelAction",new ChangePanelAction());
    if(a.getParent()!=null){
      putActionMapAction(a.getParent());
    }
  }

    private class ChangePanelAction implements Action{
      //requested methods  
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}      
            
      public void actionPerformed(java.awt.event.ActionEvent e){
        //System.err.println("ChangePanelAction");
        ChangePanelEvent ev=new ChangePanelEvent();
        ev.setSource(tree);
        ev.setLocation(getPanelLocation());
        //  System.err.println("FBTable parent:"+FBTable.this.getParent().getParent().getParent().toString());
        Broadcaster.notifyChangePanelListeners(ev);       
      }
    }  
}
