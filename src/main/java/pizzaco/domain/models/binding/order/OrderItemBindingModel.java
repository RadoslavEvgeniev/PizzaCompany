package pizzaco.domain.models.binding.order;

public class OrderItemBindingModel {

    private String name;
    private Integer quantity;
    private Boolean isChecked;

    public OrderItemBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getChecked() {
        return this.isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
