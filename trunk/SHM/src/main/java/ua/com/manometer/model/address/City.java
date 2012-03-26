package ua.com.manometer.model.address;

import javax.persistence.*;

@Entity
public class City {
	private Long id;
	private String name;
	private Area area;

	public City() {
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {

		return "{\"id\":" + id + ",\"label\":\"" + name + "\"}";
	}
}
