package application.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Profession {
	private Long id;
	private String name;
	public Profession() {
	}
	public Profession(String name) {
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
		// TODO Auto-generated method stub
		return name;
	}

}