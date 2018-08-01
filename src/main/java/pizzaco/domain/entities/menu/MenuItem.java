package pizzaco.domain.entities.menu;

import pizzaco.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class MenuItem extends BaseEntity {

    private String name;
    private BigDecimal price;

    public MenuItem() {
    }

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
