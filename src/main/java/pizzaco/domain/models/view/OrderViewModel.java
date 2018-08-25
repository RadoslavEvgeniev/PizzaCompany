package pizzaco.domain.models.view;

import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderViewModel {

    private String id;
    private UserViewModel user;
    private AddressViewModel address;
    private LocalDateTime finishedDateTime;
    private List<DrinkViewModel> drinks;
    private List<DipViewModel> dips;
    private List<PastaViewModel> pastas;
    // TODO : pizzas
    private BigDecimal totalPrice;

    public OrderViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserViewModel getUser() {
        return this.user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public AddressViewModel getAddress() {
        return this.address;
    }

    public void setAddress(AddressViewModel address) {
        this.address = address;
    }

    public LocalDateTime getFinishedDateTime() {
        return this.finishedDateTime;
    }

    public void setFinishedDateTime(LocalDateTime finishedDateTime) {
        this.finishedDateTime = finishedDateTime;
    }

    public List<DrinkViewModel> getDrinks() {
        return this.drinks;
    }

    public void setDrinks(List<DrinkViewModel> drinks) {
        this.drinks = drinks;
    }

    public List<DipViewModel> getDips() {
        return this.dips;
    }

    public void setDips(List<DipViewModel> dips) {
        this.dips = dips;
    }

    public List<PastaViewModel> getPastas() {
        return this.pastas;
    }

    public void setPastas(List<PastaViewModel> pastas) {
        this.pastas = pastas;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
