package ua.com.manometr.model.address;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Area {


    private Long id;
    private String name;
    private Country country;

    // private List<City> cities;

    public Area() {
        // TODO Auto-generated constructor stub
    }

    public Area(String name) {
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

    @ManyToOne(fetch = FetchType.EAGER)
   
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

// @OneToMany(fetch=FetchType.EAGER)
    // @JoinColumn(name="area_id")
    // public List<City> getCities() {
    // return cities;
    // }
    // public void setCities(List<City> cities) {
    // this.cities = cities;
    // }

    // public void addCity(City city) {
    // if (cities == null)
    // cities = new LinkedList<City>();
    // cities.add(city);
    // }

    @Override
    public String toString() {

        return "{\"id\":" + id + ",\"label\":\"" + name + "\"}";
    }

}
