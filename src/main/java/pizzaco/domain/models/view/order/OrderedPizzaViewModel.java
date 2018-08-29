package pizzaco.domain.models.view.order;

import pizzaco.domain.models.service.ingredients.SauceServiceModel;
import pizzaco.domain.models.service.ingredients.VegetableServiceModel;
import pizzaco.domain.models.view.ingredients.*;

import java.math.BigDecimal;
import java.util.List;

public class OrderedPizzaViewModel {

    private BigDecimal price;
    private String description;
    private SizeViewModel size;
    private DoughViewModel dough;
    private SauceServiceModel sauce;
    private List<SpiceViewModel> spices;
    private List<CheeseViewModel> cheeses;
    private List<MeatViewModel> meats;
    private List<VegetableServiceModel> vegetables;

    public OrderedPizzaViewModel() {
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

    public SizeViewModel getSize() {
        return this.size;
    }

    public void setSize(SizeViewModel size) {
        this.size = size;
    }

    public DoughViewModel getDough() {
        return this.dough;
    }

    public void setDough(DoughViewModel dough) {
        this.dough = dough;
    }

    public SauceServiceModel getSauce() {
        return this.sauce;
    }

    public void setSauce(SauceServiceModel sauce) {
        this.sauce = sauce;
    }

    public List<SpiceViewModel> getSpices() {
        return this.spices;
    }

    public void setSpices(List<SpiceViewModel> spices) {
        this.spices = spices;
    }

    public List<CheeseViewModel> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(List<CheeseViewModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<MeatViewModel> getMeats() {
        return this.meats;
    }

    public void setMeats(List<MeatViewModel> meats) {
        this.meats = meats;
    }

    public List<VegetableServiceModel> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<VegetableServiceModel> vegetables) {
        this.vegetables = vegetables;
    }
}
