package org.jfm.views.fbtable;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import org.jfm.event.*;
import org.jfm.main.*;
import org.jfm.views.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FBTable extends JTable {
  private FBFileCellRenderer renderer=new FBFileCellRenderer();
  
  public FBTable() {
    super();
    removeDefaultKeys();
    addKeys();
    //setModel(model);    
    addMouseActions();        
    setOtherProperties();
  }
  
    
  private void setOtherProperties(){
    this.getTableHeader().setReorderingAllowed(false);
    this.setShowHorizontalLines(false);
    this.setShowVerticalLines(false);
    this.setColumnSelectionAllowed(false);
    this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    this.setDragEnabled(true);
    
    final DragSource ds=new DragSource();
    ds.createDefaultDragGestureRecognizer(this,DnDConstants.ACTION_COPY_OR_MOVE,new DragGestureListener(){
      public void dragGestureRecognized(DragGestureEvent event){
        ds.startDrag(event,DragSource.DefaultCopyDrop,new Transferable(){
          public Object getTransferData(DataFlavor fl){
            return null;
          }
          
          public DataFlavor[] getTransferDataFlavors(){
            return new DataFlavor[]{new DataFlavor(org.jfm.views.FileElement.class,"JFM Files")};
          }
          
          public boolean isDataFlavorSupported(DataFlavor flavor){
            return flavor.getDefaultRepresentationClass().toString().equals(org.jfm.views.FileElement.class.toString());
          }          
        },new DragSourceAdapter(){
        });
      }
    });
    
    this.setDropTarget(new DropTarget(this,new DropTargetAdapter(){
      public void drop(DropTargetDropEvent ev){
        ev.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        DataFlavor[] fl=ev.getTransferable().getTransferDataFlavors();
        for(int i=0;i<fl.length;i++){
          System.err.println(fl[i].toString());
          try{
            Object obj=ev.getTransferable().getTransferData(fl[i]);
            System.err.println("Data is:"+obj);
          }catch(Exception ex){ex.printStackTrace();}
        }
      }
    }));
  }
    
  
  private void removeDefaultKeys(){
    KeyStroke[] strokes=this.getRegisteredKeyStrokes();
    InputMap mainMap=this.getInputMap(this.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    InputMap secondaryMap=this.getInputMap();
    InputMap map=this.getInputMap(this.WHEN_IN_FOCUSED_WINDOW);
    mainMap.clear();
    secondaryMap.clear();
    map.clear();
  }
  
  private void addKeys(){
    InputMap mainMap=this.getInputMap(this.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    InputMap secondaryMap=this.getInputMap();

    mainMap.put(KeyStroke.getKeyStroke("DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("UP"),"selectPreviousRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_UP"),"selectPreviousRow");
    mainMap.put(KeyStroke.getKeyStroke("shift DOWN"),"selectNextRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift KP_DOWN"),"selectNextRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift UP"),"selectPreviousRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift KP_UP"),"selectPreviousRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("PAGE_UP"),"scrollUpChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("PAGE_DOWN"),"scrollDownChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift PAGE_UP"),"scrollUpExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift PAGE_DOWN"),"scrollDownExtendSelection");    
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_UP"),"upOneDirectory"); //MUST ADD an action for this 
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_DOWN"),"scrollDownChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("HOME"),"selectFirstRow");
    mainMap.put(KeyStroke.getKeyStroke("END"),"selectLastRow");
    mainMap.put(KeyStroke.getKeyStroke("shift END"),"selectLastRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("shift HOME"),"selectFirstRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("ctrl A"),"selectAll");
    mainMap.put(KeyStroke.getKeyStroke("ESCAPE"),"cancel");
    mainMap.put(KeyStroke.getKeyStroke("ENTER"),"fileAction"); //MUST ADD an action for this
    this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke("ctrl R"),"refreshAction");
    mainMap.put(KeyStroke.getKeyStroke("INSERT"),"selectNextRowExtendSelection");
    
    this.getActionMap().put("fileAction",new FileAction());
    this.getActionMap().put("upOneDirectory",new UpDirectoryAction());
    this.getActionMap().put("changePanelAction",new ChangePanelAction());
    this.getActionMap().put("refreshAction",new RefreshAction());
  }
  
    public TableCellRenderer getCellRenderer(int row, int column) {
//	if (column == 0) {
//	    return null;
//	}	       
        //if(this.getColumnClass(column)==FileElement.class) 
	  return renderer;
        //else
          //return super.getDefaultRenderer(Object.class);
    }    
    
    private void addMouseActions(){
      this.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
          int row=FBTable.this.rowAtPoint(e.getPoint());
          if(e.getClickCount()==2 && (row>=0) && (row<((FBTableModel)FBTable.this.getModel()).getRowCount())){//double click on row
            processActionOnRow(row);
            return;
          }
        }
      });
      
    }
    
    private void processActionOnRow(int row){      
      FileElement el=(FileElement)((FBTableModel)FBTable.this.getModel()).getValueAt(row,((FBTableModel)FBTable.this.getModel()).getColumnIndex(FileElement.class));
      if(el.isDirectory()){
        if(el.isTopFile()){
          if(el.getParent()==null) { //top of directory tree
            return;
          }else{
            ((FBTableModel)FBTable.this.getModel()).browseDirectory(new FileElement(el.getParent()));
            int index =((FBTableModel)FBTable.this.getModel()).getCurrentFiles().indexOf(el);
            
              if(index>=0){                
                this.getSelectionModel().setSelectionInterval(index,index);
                //this.scrollRectToVisible();
              }else{
                this.getSelectionModel().setSelectionInterval(0,0);
              }
          }
        }
        else{
          ((FBTableModel)FBTable.this.getModel()).browseDirectory(el);
          this.getSelectionModel().setSelectionInterval(0,0);
        }
      }
      else{
        /**@@todo see what happens when we try to execute a file*/ 
      }
    }

    private class RefreshAction implements Action{
      //requested methods  
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}      
            
      public void actionPerformed(java.awt.event.ActionEvent e){
        FileElement el=(FileElement)((FBTableModel)FBTable.this.getModel()).getValueAt(0,((FBTableModel)FBTable.this.getModel()).getColumnIndex(FileElement.class));
        ((FBTableModel)FBTable.this.getModel()).browseDirectory(el);
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
        ev.setSource(FBTable.this);
        ev.setLocation(((FBTableModel)FBTable.this.getModel()).getPart());
        //  System.err.println("FBTable parent:"+FBTable.this.getParent().getParent().getParent().toString());
        Broadcaster.notifyChangePanelListeners(ev);       
      }
    }
    
    private class FileAction implements Action{
      //requested methods  
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}      
            
      public void actionPerformed(java.awt.event.ActionEvent e){
          int row=((FBTable)e.getSource()).getSelectedRow();
          if(row>=0 && row<((FBTableModel)FBTable.this.getModel()).getRowCount()){
                  processActionOnRow(row);
          }     
      }
    }
    
    private class UpDirectoryAction implements Action{
      //requested methods  
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}      
            
      public void actionPerformed(java.awt.event.ActionEvent e){
        processActionOnRow(0);     
      }
    }      
}
