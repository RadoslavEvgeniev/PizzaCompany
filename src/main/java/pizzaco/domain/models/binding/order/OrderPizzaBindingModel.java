package pizzaco.domain.models.binding.order;

import java.math.BigDecimal;
import java.util.List;

public class OrderPizzaBindingModel {

    private String name;
    private BigDecimal price;
    private String description;
    private String size;
    private String dough;
    private String sauce;
    private List<String> spices;
    private List<String> cheeses;
    private List<String> vegetables;
    private List<String> meats;
    private boolean isAdded;


    public OrderPizzaBindingModel() {
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

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDough() {
        return this.dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
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

    public List<String> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<String> vegetables) {
        this.vegetables = vegetables;
    }

    public List<String> getMeats() {
        return this.meats;
    }

    public void setMeats(List<String> meats) {
        this.meats = meats;
    }

    public boolean isAdded() {
        return this.isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
