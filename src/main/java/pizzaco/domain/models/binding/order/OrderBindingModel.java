package pizzaco.domain.models.binding.order;

public class OrderBindingModel {

    private String name;
    private Boolean isChecked;

    public OrderBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return this.isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
