package application.tests.testInheritance;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class Item {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long id;
    private String name;
    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    private Project project;
        public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
