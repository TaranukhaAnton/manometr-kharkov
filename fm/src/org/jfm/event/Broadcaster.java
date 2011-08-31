package org.jfm.event;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;


/**
  * This is the class that registers the listeners, removes listeners, and notifies the listeners when a certain event occurs.
  */
public class Broadcaster
{

  private static HashMap hash = new HashMap();

  private static Hashtable getHash(Object obj)
  {
    if(hash.containsKey(obj))
      return (Hashtable)hash.get(obj);
    else
    {
      Hashtable wh = new Hashtable();
      hash.put(obj, wh);
      return wh;
    }
  }

  private static synchronized void addListener(Object listenerClass, BroadcastListener listener)
  {
    Hashtable wh = getHash(listenerClass);
    wh.put(listener, listener);
  }

  private static synchronized void removeListener(Object listenerClass, BroadcastListener listener)
  {
   if((listenerClass!=null) && (listener!=null))
   {
      Hashtable wh = getHash(listenerClass);
      wh.remove(listener);
      if(wh.size()==0)
        hash.remove(listenerClass);
   }
  }

  private static Iterator getListenerIterator(Object listenerClass)
  {
    Hashtable wh = getHash(listenerClass);
    return wh.values().iterator();
  }

  /**Adds a listener who listens for the ChangeDirectoryEvent*/
  public static void addChangeDirectoryListener(ChangeDirectoryListener listener)
  {
    addListener(ChangeDirectoryListener.class, listener);
  }

  /**Removes a listener who listens for the ChangeDirectoryEvent*/
  public static void removeChangeDirectoryListener(ChangeDirectoryListener listener)
  {
    removeListener(ChangeDirectoryListener.class, listener);
  }

  /**Notifies all listeners who listens for the ChangeDirectoryEvent*/
  public static void notifyChangeDirectoryListeners(ChangeDirectoryEvent event)
  {

      Iterator it = getListenerIterator(ChangeDirectoryListener.class);
      ChangeDirectoryListener listener;
      while(it.hasNext())
      { 
        listener = (ChangeDirectoryListener)it.next();
        listener.changeDirectory(event);
      }            
  }
  

  /**Adds a listener who listens for the ChangePanelEvent*/
  public static void addChangePanelListener(ChangePanelListener listener)
  {
    addListener(ChangePanelListener.class, listener);
  }

  /**Removes a listener who listens for the ChangePanelEvent*/
  public static void removeChangePanelListener(ChangePanelListener listener)
  {
    removeListener(ChangePanelListener.class, listener);
  }

  /**Notifies all listeners who listens for the ChangePanelEvent*/
  public static void notifyChangePanelListeners(final ChangePanelEvent event)
  {

      Iterator it = getListenerIterator(ChangePanelListener.class);
      ChangePanelListener listener;
      while(it.hasNext())
      {
        listener = (ChangePanelListener)it.next();
        listener.changePanel(event);
      }            
  }

  /**Adds a listener who listens for the ChangeViewEvent*/
  public static void addChangeViewListener(ChangeViewListener listener)
  {
    addListener(ChangeViewListener.class, listener);
  }

  /**Removes a listener who listens for the ChangeViewEvent*/
  public static void removeChangeViewListener(ChangeViewListener listener)
  {
    removeListener(ChangeViewListener.class, listener);
  }

  /**Notifies all listeners who listens for the ChangeViewEvent*/
  public static void notifyChangeViewListeners(ChangeViewEvent event)
  {

      Iterator it = getListenerIterator(ChangeViewListener.class);
      ChangeViewListener listener;
      while(it.hasNext())
      {
        listener = (ChangeViewListener)it.next();
        listener.viewChanged(event);
      }            
  }
  
  /**Adds a listener who listens for the FileListSelectionEvent*/
  public static void addFileListSelectionListener(FileListSelectionListener listener)
  {
    addListener(FileListSelectionListener.class, listener);
  }

  /**Removes a listener who listens for the FileListSelectionEvent*/
  public static void removeFileListSelectionListener(FileListSelectionListener listener)
  {
    removeListener(FileListSelectionListener.class, listener);
  }

  /**Notifies all listeners who listens for the FileListSelectionEvent*/
  public static void notifyFileListSelectionListener(FileListSelectionEvent event)
  {

      Iterator it = getListenerIterator(FileListSelectionListener.class);
      FileListSelectionListener listener;
      while(it.hasNext())
      {
        listener = (FileListSelectionListener)it.next();
        listener.fileListSelectionChanged(event);
      }            
  }
  
//  /**Adds a listener who listens for the ElementChangeEvent*/
//  public static void addElementChangeListener(ElementChangeListener listener)
//  {
//    addListener(ElementChangeListener.class, listener);
//  }
//
//  /**Removes a listener who listens for the ElementChangeEvent*/
//  public static void removeElementChangeListener(ElementChangeListener listener)
//  {
//    removeListener(ElementChangeListener.class, listener);
//  }
//
//  /**Notifies all listeners who listens for the ElementChangeEvent*/
//  public static void notifyElementChangeListeners(ElementChangeEvent event)
//  {
//    Iterator it = getListenerIterator(ElementChangeListener.class);
//    ElementChangeListener listener;
//    while(it.hasNext())
//    {
//      listener = (ElementChangeListener)it.next();
//      listener.elementChanged(event);
//    }
//  }

}

