package pizzaco.domain.models.service;

import pizzaco.domain.entities.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceModel {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<OrderServiceModel> orders;
    private List<AddressServiceModel> addresses;
    private List<UserRoleServiceModel> authorities;

    public UserServiceModel() {
        this.addresses = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderServiceModel> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderServiceModel> orders) {
        this.orders = orders;
    }

    public List<AddressServiceModel> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<AddressServiceModel> addresses) {
        this.addresses = addresses;
    }

    public List<UserRoleServiceModel> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<UserRoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
