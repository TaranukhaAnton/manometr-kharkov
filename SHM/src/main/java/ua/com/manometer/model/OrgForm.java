package ua.com.manometer.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Locale;

@Entity
public class OrgForm {
    private Integer id;
    private String name;
    private String nameUkr;
    private String nameEng;

    //private List<City> regions;
    public OrgForm() {

    }

    public OrgForm(String name) {
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


    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    @Override
    public String toString() {

        return name;
    }

    public String getLocalizedName(Locale locale) {

        if ((new Locale("ru", "RU")).equals(locale)) {
            return name;
        } else if ((new Locale("ua", "UA")).equals(locale)) {
            return nameUkr;
        } else if ((new Locale("en", "EN")).equals(locale)) {
            return nameEng;
        } else {
            return "error. Locale not found!!!";
        }

    }


}