package pizzaco.domain.models.service;

import pizzaco.domain.entities.Address;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<AddressServiceModel> addresses;

    public UserServiceModel() {
        this.addresses = new HashSet<>();
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

    public Set<AddressServiceModel> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<AddressServiceModel> addresses) {
        this.addresses = addresses;
    }
}
