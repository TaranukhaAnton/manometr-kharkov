package application.tests.testInheritance;




import javax.persistence.*;

@Entity
@DiscriminatorValue("SMALL")
 
public class SmallProject extends Project   {

    private String sub1Property;

    public String getSub1Property() {
        return sub1Property;
    }

    public void setSub1Property(String sub1Property) {
        this.sub1Property = sub1Property;
    }
}