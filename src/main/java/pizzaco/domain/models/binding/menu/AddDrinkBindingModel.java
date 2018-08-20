package pizzaco.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddDrinkBindingModel {

    private String name;
    private BigDecimal price;
    private MultipartFile drinkImage;

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

    public MultipartFile getDrinkImage() {
        return this.drinkImage;
    }

    public void setDrinkImage(MultipartFile drinkImage) {
        this.drinkImage = drinkImage;
    }
}
