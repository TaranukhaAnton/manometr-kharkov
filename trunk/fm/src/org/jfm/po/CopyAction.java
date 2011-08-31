package org.jfm.po;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import org.jfm.views.FileElement;
import org.jfm.main.Options;
import org.jfm.po.copy.*;
import org.jfm.event.*;
import java.io.*;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class CopyAction implements Action {

  private long totalFilesSizes=0;
  private long totalBytesWritten=0;
  private boolean overwriteAll=false;
  private boolean skipAll=false;
  private boolean cancel=false;
  private ProgressActionDialog progress=null;
  private Vector filesToBeCopied=new Vector();
  private FileElement toDir;
  private File curentlyCopiedFile;
  
  public CopyAction() {
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
     //the vectors should never be null, and should contain at least one element
     //it's the views bussiness to do this and THEY MUST DO THIS,unless it could get ugly
     
     int[] indexes;


     if(Options.getActivePanel()==Options.LEFT_PANEL){
      filesToBeCopied=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
      toDir=(FileElement)Options.getRightFiles().elementAt(0);
     }else{
      filesToBeCopied=Options.getRightFiles();
      indexes=Options.getRightPanelSelections();
      toDir=(FileElement)Options.getLeftFiles().elementAt(0);
     }

    if(filesToBeCopied==null || filesToBeCopied.size()==0){
      return;
    }

    CopyConfirmDialog d=new CopyConfirmDialog(Options.getMainFrame(),"Copy",true);
    if(filesToBeCopied.size()==1){
      d.setCopyFrom(((FileElement)filesToBeCopied.elementAt(indexes[0])).getPath());
    }else{
      d.setCopyFrom(((FileElement)filesToBeCopied.elementAt(0)).getPath());
    }

    d.setCopyTo(toDir.getPath());
    d.setLocationRelativeTo(Options.getMainFrame());
    d.show();
    if(d.isCancelled()) return;
    
    Vector f=new Vector();
    for(int i=indexes[0];i<=indexes[1];i++){
      if(i==0) continue;
      f.addElement(filesToBeCopied.elementAt(i));
    }
    
    filesToBeCopied=f;
    
    progress=new ProgressActionDialog(Options.getMainFrame(),"Copy",true);
    progress.setLocationRelativeTo(Options.getMainFrame());

    progress.startAction(new ActionExecuter(){
      public void start(){
        try {
          copyFiles();
        }
        catch (ActionCancelledException ex) {
          this.cancel();
        }

        ChangeDirectoryEvent ev=new ChangeDirectoryEvent();
        ev.setSource(this);  
        Broadcaster.notifyChangeDirectoryListeners(ev);
      }
      
      public void cancel(){
        cancel=true;   
      }
    });
        
  }
  
  private long getFilesSize(java.util.List l){
    long totalSizes=0;
    for(int i=0;i<l.size();i++){
      File f=(File)l.get(i);
      if(f.isDirectory()){
        totalSizes+=getFilesSize(java.util.Arrays.asList(f.listFiles()));
      }else{
        totalSizes+=f.length();
      }
    }
    return totalSizes;
  }

  private void copyDir(File dir,File dest) throws ActionCancelledException{
    File[] f=dir.listFiles();
    if(f==null) return;
    //File destFile=new File(toDir.getPath()+(toDir.getPath().endsWith(File.separator)?"":File.separator)+el.getName());
    for(int i=0;i<f.length;i++){
      File destFile=new File(dest.getPath()+(dest.getPath().endsWith(File.separator)?"":File.separator)+f[i].getName());
      if(f[i].isDirectory()){
        destFile.mkdir();
        copyDir(f[i],destFile);
      }else{
        copyFile(f[i],destFile);
      }
    }
  }

  private void copyFile(File fin,File fout) throws ActionCancelledException {
    boolean append=false;
    
    if(fout.exists() && !overwriteAll && !skipAll){
      
      java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss");
      
      String message="Target file "+fout.getPath()+"  already exists."+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "Source last modified date: "+format.format(new java.util.Date(fin.lastModified()))+System.getProperty("line.separator")+
                     "Target last modified date: "+format.format(new java.util.Date(fout.lastModified()))+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "What should I do?";
      String[] buttons=new String[]{"Overwrite","Overwrite all","Skip","Skip all","Append","Cancel"};                     
 
      int result=JOptionPane.showOptionDialog(progress,message,"File exists",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,buttons,buttons[2]);
 
      switch (result){
        case 0:
          break;
        case 1:
          overwriteAll=true;
          break;
        case 2:
          totalBytesWritten+=fin.length();
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          progress.setTotalProgresssValue(t_percent);
          return;
        case 3:
          skipAll=true;
          break;
          case 4:
          append=true;
            break;
          case 5:
            throw new ActionCancelledException();
      }
    }
    
    if(fout.exists() && skipAll){
          totalBytesWritten+=fin.length();
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          progress.setTotalProgresssValue(t_percent);
          progress.setFileProgresssValue(100);
          return;      
    }
      FileInputStream in=null;
      FileOutputStream out=null;
      try{
        in=new FileInputStream(fin);
        out=new FileOutputStream(fout.getPath(),append);
        progress.setFileProgresssValue(0);
        curentlyCopiedFile=fout;
        byte[] data=new byte[1024];
        int read=0;
        long bytesWrote=0;
        long f_length=fin.length();
        
        /**@@todo Maybe async IO would be nice here**/
        while((read=in.read(data))>=0){
          if(cancel){
            throw new ActionCancelledException();
          }
          
          out.write(data,0,read);
          bytesWrote+=read;
          totalBytesWritten+=read;          
          int f_percent=(int)((bytesWrote*100)/f_length);          
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          progress.setFileProgresssValue(f_percent);
          progress.setTotalProgresssValue(t_percent);
        }
        progress.setFileProgresssValue(100);
      }catch(ActionCancelledException ex){
          curentlyCopiedFile.delete();
          throw ex;       
      }catch(Exception ex){             
        JOptionPane.showMessageDialog(progress,"Error while writing "+fout.getPath(),"Error",JOptionPane.ERROR_MESSAGE);
        curentlyCopiedFile.delete();
      }finally{
        try {
          in.close();
          out.close(); 
        }
        catch (Exception ignored) {}
      }        
  }
  
  private void copyFiles() throws ActionCancelledException{
    totalFilesSizes=getFilesSize(filesToBeCopied);
    if(filesToBeCopied.size()==0) progress.dispose();
    for(int i=0;i<filesToBeCopied.size();i++){
      FileElement el=(FileElement)filesToBeCopied.elementAt(i);
      File destFile=new File(toDir.getPath()+(toDir.getPath().endsWith(File.separator)?"":File.separator)+el.getName());
      if(el.isDirectory()){
        if(!destFile.exists()) destFile.mkdirs();
        copyDir(el,destFile);
      }else{
        copyFile(el,destFile);
      }
      
    }
  }  
}