package application.data.address;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Country {
	private Long id;
	private String name;
	private String nameUkr;
	//private List<City> regions;
	public Country() {
		// TODO Auto-generated constructor stub
	}
	public Country(String name) {
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

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }
}
