package application.tests;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class testData {
    @Id
    @GeneratedValue()
    private Long id;
   
    @Type(type = "application.hibernate.LongArrayCustomType")
    private Long[] tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getTags() {
        return tags;
    }

    public void setTags(Long[] tags) {
        this.tags = tags;
    }
}
