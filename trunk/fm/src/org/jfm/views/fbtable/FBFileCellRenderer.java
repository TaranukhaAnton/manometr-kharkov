package org.jfm.views.fbtable;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.*;
import java.awt.Color;
import org.jfm.views.*;
/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FBFileCellRenderer extends DefaultTableCellRenderer {

  private Color unselectedForeground; 
  private Color unselectedBackground; 

  public FBFileCellRenderer() {
    super();
  }
  
  public void setForeground(Color c) {
     super.setForeground(c); 
     unselectedForeground = c; 
  }
    
  public void setBackground(Color c) {
     super.setBackground(c); 
     unselectedBackground = c; 
  }

  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
    Component c=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    if(!(c instanceof JLabel)) return c;
   
    JLabel label=(JLabel)c;    
    setBorder(noFocusBorder);
            
    if(column==table.getTableHeader().getColumnModel().getColumnIndex("Size")){
      label.setHorizontalAlignment(JLabel.RIGHT);
    }else{
        label.setHorizontalAlignment(JLabel.LEFT);
    }    

    if(!(value instanceof FileElement)){
     label.setIcon(null); 
     return label;
    }
    else{    
      FileElement element=(FileElement)value;
      if(element.isDirectory()){
        if(element.isTopFile()){
          label.setIcon((Icon)UIManager.get("FileChooser.upFolderIcon"));
        }else{
          label.setIcon((Icon)UIManager.get("FileView.directoryIcon"));
        }
      }else{
        label.setIcon((Icon)UIManager.get("FileView.fileIcon"));
      }
        
      return label;
    }      
  } 
  
}
