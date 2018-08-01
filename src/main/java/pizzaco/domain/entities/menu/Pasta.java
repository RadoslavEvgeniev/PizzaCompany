package pizzaco.domain.entities.menu;

import org.hibernate.annotations.GenericGenerator;
import pizzaco.domain.entities.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pastas")
public class Pasta extends MenuItem {

    private String description;

    public Pasta() {
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
