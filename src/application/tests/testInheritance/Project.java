package application.tests.testInheritance;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@org.hibernate.annotations.ForceDiscriminator
@Table(name = "Project")


public abstract class Project {

    @Id
    @GeneratedValue()
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String baseProperty;

    public String getBaseProperty() {
        return baseProperty;
    }

    public void setBaseProperty(String baseProperty) {
        this.baseProperty = baseProperty;
    }
}