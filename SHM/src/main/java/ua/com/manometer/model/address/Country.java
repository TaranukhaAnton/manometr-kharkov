package ua.com.manometer.model.address;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Country {
	private Integer id;
	private String name;
	//private List<City> regions;
	public Country() {
	}
	public Country(String name) {
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
		return name;
	}

}
