package pizzaco.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddPizzaBindingModel {

    private String name;
    private String sauce;
    private List<String> spices;
    private List<String> cheeses;
    private List<String> meats;
    private List<String> vegetables;
    private MultipartFile pizzaImage;

    public AddPizzaBindingModel() {
        this.spices = new ArrayList<>();
        this.cheeses = new ArrayList<>();
        this.meats = new ArrayList<>();
        this.vegetables = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSauce() {
        return this.sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public List<String> getSpices() {
        return this.spices;
    }

    public void setSpices(List<String> spices) {
        this.spices = spices;
    }

    public List<String> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(List<String> cheeses) {
        this.cheeses = cheeses;
    }

    public List<String> getMeats() {
        return this.meats;
    }

    public void setMeats(List<String> meats) {
        this.meats = meats;
    }

    public List<String> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<String> vegetables) {
        this.vegetables = vegetables;
    }

    public MultipartFile getPizzaImage() {
        return this.pizzaImage;
    }

    public void setPizzaImage(MultipartFile pizzaImage) {
        this.pizzaImage = pizzaImage;
    }
}
