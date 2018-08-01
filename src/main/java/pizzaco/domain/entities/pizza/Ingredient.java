package pizzaco.domain.entities.pizza;

import pizzaco.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Ingredient extends BaseEntity {

    private String name;

    public Ingredient() {
    }

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
