package pizzaco.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddDipBindingModel {

    private String name;
    private BigDecimal price;
    private MultipartFile dipImage;

    public AddDipBindingModel() {
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

    public MultipartFile getDipImage() {
        return this.dipImage;
    }

    public void setDipImage(MultipartFile dipImage) {
        this.dipImage = dipImage;
    }
}
