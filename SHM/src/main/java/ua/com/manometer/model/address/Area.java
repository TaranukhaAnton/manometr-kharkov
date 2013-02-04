package ua.com.manometer.model.address;


import javax.persistence.*;

@Entity
public class Area {


    private Integer id;
    private String name;
    private Country country;

    // private List<City> cities;

    public Area() {
    }

    public Area(String name) {
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

    @ManyToOne(fetch = FetchType.EAGER)
   
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setId(Integer id) {
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
