package ua.com.manometer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Profession {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Profession() {
    }




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
        // TODO Auto-generated method stub
        return name;
    }

}