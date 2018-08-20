package pizzaco.domain.models.binding.ingredients;

import java.math.BigDecimal;

public class AddMeatBindingModel {

    private String name;
    private BigDecimal price;

    public AddMeatBindingModel() {
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
