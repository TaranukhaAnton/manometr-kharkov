package application.tests.testInheritance;

import javax.persistence.*;

@Entity
@DiscriminatorValue("LARGE")



public class LargeProject extends Project {

    private String sub2Property;

    private String joinedProperty;

    public String getSub2Property() {
        return sub2Property;
    }

    public void setSub2Property(String sub2Property) {
        this.sub2Property = sub2Property;
    }

    public String getJoinedProperty() {
        return joinedProperty;
    }

    public void setJoinedProperty(String joinedProperty) {
        this.joinedProperty = joinedProperty;
    }
}