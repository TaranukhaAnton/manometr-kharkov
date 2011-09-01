package org.jfm.event;


/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class ChangeDirectoryEvent extends BroadcastEvent {

  public ChangeDirectoryEvent() {
    this(null);
  }
  
  public ChangeDirectoryEvent(String dir) {
    setDirectory(dir);
  }

  private String directory;
  public String getDirectory() {
    return directory;
  }
  public void setDirectory(String directory) {
    this.directory = directory;
  }
}