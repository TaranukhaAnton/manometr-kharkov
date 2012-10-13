package application.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class OrgForm {
	private Long id;
	private String name;
	//private List<City> regions;
	public OrgForm() {

	}
	public OrgForm(String name) {
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

		return name;
	}

}