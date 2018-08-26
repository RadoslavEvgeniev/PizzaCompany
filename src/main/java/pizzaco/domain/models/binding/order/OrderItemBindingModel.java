package pizzaco.domain.models.binding.order;

public class OrderItemBindingModel {

    private String name;
    private boolean isAdded;

    public OrderItemBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdded() {
        return this.isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
