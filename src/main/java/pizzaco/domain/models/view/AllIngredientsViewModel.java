package pizzaco.domain.models.view;

import pizzaco.domain.models.view.ingredients.*;

import java.util.List;

public class AllIngredientsViewModel {

    private List<SizeViewModel> sizes;
    private List<DoughViewModel> doughs;
    private List<SauceViewModel> sauces;
    private List<SpiceViewModel> spices;
    private List<CheeseViewModel> cheeses;
    private List<MeatViewModel> meats;
    private List<VegetableViewModel> vegetables;

    public AllIngredientsViewModel() {
    }

    public List<SizeViewModel> getSizes() {
        return this.sizes;
    }

    public void setSizes(List<SizeViewModel> sizes) {
        this.sizes = sizes;
    }

    public List<DoughViewModel> getDoughs() {
        return this.doughs;
    }

    public void setDoughs(List<DoughViewModel> doughs) {
        this.doughs = doughs;
    }

    public List<SauceViewModel> getSauces() {
        return this.sauces;
    }

    public void setSauces(List<SauceViewModel> sauces) {
        this.sauces = sauces;
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

    public List<VegetableViewModel> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<VegetableViewModel> vegetables) {
        this.vegetables = vegetables;
    }
}
