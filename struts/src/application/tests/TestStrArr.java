package application.tests;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 12.08.2010
 * Time: 23:07:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TestStrArr {
    @Id
    @GeneratedValue()
    private Long id;

    @Type(type = "application.hibernate.LongArrayCustomType")
    private String[] tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
