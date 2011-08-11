package ua.com.manometer.model.address;

import javax.persistence.*;

@Entity
public class Region {
    private Long id;
    private String name;


    public Region() {

    }

    public Region(String name) {
        this.name = name;
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
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
