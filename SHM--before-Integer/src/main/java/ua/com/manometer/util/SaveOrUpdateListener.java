package ua.com.manometer.util;

import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 04.01.13
 * Time: 8:07
 * To change this template use File | Settings | File Templates.
 */
public class SaveOrUpdateListener  extends DefaultSaveOrUpdateEventListener {
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
        System.out.println("bingo!!!");
        super.onSaveOrUpdate(event);
    }
}