package org.jfm.event;

/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class ChangeViewEvent extends BroadcastEvent {

  public ChangeViewEvent() {
  }
  
  private int newView;
  public int getNewView() {
    return newView;
  }
  public void setNewView(int newView) {
    this.newView = newView;
  }
  
}