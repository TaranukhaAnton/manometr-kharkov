package application.data;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SupTest {
    @Id
    @GeneratedValue()
    Long id;
    String alias; //условное название

    @Column(name="aaa", nullable = false, columnDefinition = "char(1) NOT NULL default 'N'")
    @Type(type = "yes_no")
    private boolean purposeForItself;


    public boolean isPurposeForItself() {
        return purposeForItself;
    }
    public void setPurposeForItself(boolean purposeForItself) {
        this.purposeForItself = purposeForItself;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
