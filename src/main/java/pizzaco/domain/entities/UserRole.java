package pizzaco.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity implements GrantedAuthority {

    private String authority;

    public UserRole() {
    }

    public UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    @Column(name = "role", nullable = false, unique = true)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
