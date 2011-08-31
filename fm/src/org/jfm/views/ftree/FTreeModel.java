package org.jfm.views.ftree;

import javax.swing.tree.*;
import javax.swing.SwingUtilities;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import org.jfm.event.*;
import org.jfm.main.Options;
import org.jfm.views.FileElement;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FTreeModel extends DefaultTreeModel {
  
  private int location;
  private DefaultMutableTreeNode root;
  
  public FTreeModel(int location,DefaultMutableTreeNode root) {
    super(root);
    this.root=root;
    root.add(new DefaultMutableTreeNode("Loading..."));
    setLocation(location);
    initTree(Options.getStartDirectory());
  }
  
  public void setLocation(int l){
    location=l;
  }
  
  public void initTree(String dir){
    FileElement f=new FileElement(dir);
    FileElement rootFile=getRootFile(f);
    root.setUserObject(rootFile);
    fillTreeFromNode(root);
  }
  
  public void fillTreeFromNode(DefaultMutableTreeNode n){
    
    FileElement el=(FileElement)n.getUserObject();    
    if(!el.isDirectory()) return;        
    n.removeAllChildren();
    File[] paths=el.listFiles();
    
    FileElement[] childs=new FileElement[paths.length];
    for(int i=0;i<paths.length;i++){
      childs[i]=new FileElement(paths[i].getPath());
    }
    
    Arrays.sort(childs);
    
    for(int i=0;i<childs.length;i++){
      DefaultMutableTreeNode a_node=new DefaultMutableTreeNode(childs[i]);
      if(childs[i].isDirectory()) a_node.add(new DefaultMutableTreeNode("Loading..."));
      n.add(a_node);
    }
    
    this.nodeStructureChanged(n);
  }
  
  private FileElement getRootFile(FileElement f){
    return f.getRootFile();
  }
}