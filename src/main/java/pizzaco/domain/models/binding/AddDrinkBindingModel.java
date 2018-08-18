package pizzaco.domain.models.binding;

import java.math.BigDecimal;

public class AddDrinkBindingModel {

    private String name;
    private BigDecimal price;

    public AddDrinkBindingModel() {
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
