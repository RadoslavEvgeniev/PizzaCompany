package pizzaco.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserEditBindingModel {

    private String id;
    private String email;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    public UserEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "Email cannot be null.")
    @NotEmpty(message = "Email cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email.")
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

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = "First name cannot be null.")
    @NotEmpty(message = "First name cannot be empty.")
    @Length(min = 2, message = "First name must be at least 2 symbols long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "First name must start with capital letter.")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name cannot be null.")
    @NotEmpty(message = "Last name cannot be empty.")
    @Length(min = 2, message = "Last name must be at least 2 symbols long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "Last name must start with capital letter.")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
