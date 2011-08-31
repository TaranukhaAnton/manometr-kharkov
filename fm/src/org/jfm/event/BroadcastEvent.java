package org.jfm.event;

/**
 * This is the base class for any event
 */
public class BroadcastEvent {

    private Object source;
    private Object userObj;

    protected BroadcastEvent() {
    }

    /**
     * Constructs an event with the specified source.
     */
    public BroadcastEvent(Object source) {
        this.source = source;
    }

    /**
     * Returns the source.
     */
    public Object getSource() {
        return source;
    }

    /**
     * Sets the source who generated the event.
     */
    public void setSource(Object newSource) {
        source = newSource;
    }

    /**
     * Sets the user object.
     */
    public void setUserObject(Object obj) {
        userObj = obj;
    }

    /**
     * Returns the user object.
     */
    public Object getUserObject() {
        return userObj;
    }

}

