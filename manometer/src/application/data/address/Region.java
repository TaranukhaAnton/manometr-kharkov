package application.data.address;

import javax.persistence.*;

@Entity
public class Region {
	private Long id;
	private String name;
	private String nameUkr;

	
	public Region() {
		// TODO Auto-generated constructor stub
	}
	public Region(String name) {
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

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    @Override
	public String toString() {

		return "{\"id\":" + id + ",\"label\":\"" + name + "\"}";
		//return "\""+name+"\"";
	}
}
