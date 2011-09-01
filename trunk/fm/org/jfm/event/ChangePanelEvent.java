package org.jfm.event;


/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class ChangePanelEvent extends BroadcastEvent {

  public ChangePanelEvent() {
  }
  private int location;
  public int getLocation() {
    return location;
  }
  public void setLocation(int location) {
    this.location = location;
  }
}
