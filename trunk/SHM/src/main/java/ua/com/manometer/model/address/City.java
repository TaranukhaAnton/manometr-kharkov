package ua.com.manometer.model.address;

import javax.persistence.*;

@Entity
public class City {
	private Long id;
	private String name;
    private String nameUkr;
    private String nameEn;
	private Area area;

	public City() {
	}

	public City(String name) {
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

	@ManyToOne(fetch= FetchType.EAGER)
	
	public Area getArea() {
		return area;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArea(Area area) {
		this.area = area;
	}

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
	public String toString() {

		return "{\"id\":" + id + ",\"label\":\"" + name + "\"}";
	}
}
