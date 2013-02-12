package com.sp.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@MappedSuperclass
public class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public BaseModel() { }

    public String toOptionText() {
        return Integer.toString(id);  
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void copyTo(BaseModel copy) {
        copy.id = id;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getTypeDescr() {
        return this.getClass().getSimpleName();
    }

    //for flex serialization
    public void setTypeDescr(String str) { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseModel))
            return false;

        BaseModel model = (BaseModel) o;

        return id == model.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}


