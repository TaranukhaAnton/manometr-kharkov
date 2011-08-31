package org.jfm.main;

  import java.awt.Component;
  import org.jfm.views.*;
  
/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class ViewportOptions {
  
  public static final int FILE_BROWSER = 1;
  public static final int FILE_VIEWER  = 2;
  public static final int TREE_BROWSER  = 3;
     
  private static int leftType; 
  private static int rightType;
  
  public static JFMPanel getLeftViewComponent(int type){
    leftType=type;
    switch(type){
      case FILE_BROWSER:
        return new FileBrowserPanel();
      case FILE_VIEWER:
        return new FileViewPanel();
       case TREE_BROWSER:
        return new TreeViewPanel();
      default:
        throw new InternalError("Unrecognized left component view type");
    }
    
  }
  
  public static JFMPanel getRightViewComponent(int type){
    rightType=type;
    switch(type){
      case FILE_BROWSER:
        return new FileBrowserPanel();
      case FILE_VIEWER:
        return new FileViewPanel();
       case TREE_BROWSER:
        return new TreeViewPanel();
      default:
        throw new InternalError("Unrecognized left component view type");
    }
  }
  
  public static int getCurrentLeftViewType(){
    return leftType;
  }
  
  public static int getCurrentRightViewType(){
    return rightType;
  }
}