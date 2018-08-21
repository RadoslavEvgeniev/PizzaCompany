package pizzaco.domain.models.view.ingredients;

import java.math.BigDecimal;

public class VegetableViewModel {

    private String name;
    private BigDecimal price;

    public VegetableViewModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
