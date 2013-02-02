package ua.com.manometer.dao;

import java.io.Serializable;

public interface BaseDAO {

    public void saveOrUpdate(Object object);

    public void removeById(Serializable id, Class aClass);

    public Object getById(Serializable id, Class aClass);

}
