package pizzaco.domain.models.service.order;

import pizzaco.domain.models.service.ingredients.*;

import java.math.BigDecimal;
import java.util.List;

public class OrderedPizzaServiceModel {

    private String id;
    private BigDecimal price;
    private String description;
    private SizeServiceModel size;
    private DoughServiceModel dough;
    private SauceServiceModel sauce;
    private List<SpiceServiceModel> spices;
    private List<CheeseServiceModel> cheeses;
    private List<MeatServiceModel> meats;
    private List<VegetableServiceModel> vegetables;

    public OrderedPizzaServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public SizeServiceModel getSize() {
        return this.size;
    }

    public void setSize(SizeServiceModel size) {
        this.size = size;
    }

    public DoughServiceModel getDough() {
        return this.dough;
    }

    public void setDough(DoughServiceModel dough) {
        this.dough = dough;
    }

    public SauceServiceModel getSauce() {
        return this.sauce;
    }

    public void setSauce(SauceServiceModel sauce) {
        this.sauce = sauce;
    }

    public List<SpiceServiceModel> getSpices() {
        return this.spices;
    }

    public void setSpices(List<SpiceServiceModel> spices) {
        this.spices = spices;
    }

    public List<CheeseServiceModel> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(List<CheeseServiceModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<MeatServiceModel> getMeats() {
        return this.meats;
    }

    public void setMeats(List<MeatServiceModel> meats) {
        this.meats = meats;
    }

    public List<VegetableServiceModel> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<VegetableServiceModel> vegetables) {
        this.vegetables = vegetables;
    }
}
