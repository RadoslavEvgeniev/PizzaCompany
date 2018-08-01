package pizzaco.domain.entities.pizza;

import org.hibernate.annotations.GenericGenerator;
import pizzaco.domain.entities.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    private String size;
    private BigDecimal price;
    private Integer numberOfSlices;

    public Size() {
    }

    @Column(name = "size", nullable = false, unique = true, updatable = false)
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "number_of_slices", nullable = false, unique = true, updatable = false)
    public Integer getNumberOfSlices() {
        return this.numberOfSlices;
    }

    public void setNumberOfSlices(Integer numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }
}
