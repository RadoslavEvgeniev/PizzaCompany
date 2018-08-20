package pizzaco.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddPastaBindingModel {

    private String name;
    private BigDecimal price;
    private String description;
    private MultipartFile pastaImage;

    public AddPastaBindingModel() {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPastaImage() {
        return this.pastaImage;
    }

    public void setPastaImage(MultipartFile pastaImage) {
        this.pastaImage = pastaImage;
    }
}
