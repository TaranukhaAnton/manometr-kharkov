package ua.com.manometer.model.address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Region {
    private Integer id;
    private String name;


    public Region() {
    }

    public Region(String name) {
        this.name = name;
    }


    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return "{\"id\":" + id + ",\"label\":\"" + name + "\"}";
        //return "\""+name+"\"";
    }
}
