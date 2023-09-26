package com.foro.apiforo.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    private String name;

    private String lastname;

    private boolean flag;

    public User(InsertDataUser insertDataUser) {
        this.flag = true;
        this.email = insertDataUser.email();
        this.password = hashPassword(insertDataUser.password());
        this.userType = insertDataUser.userType();
        this.name = insertDataUser.name();
        this.lastname = insertDataUser.lastname();
    }

    public void updateData (UpdateDataUser updateDataUser){
        if (updateDataUser.password() != null){
            this.password = updateDataUser.password();
        }
        if (updateDataUser.name() != null){
            this.name = updateDataUser.name();
        }
        if (updateDataUser.lastname() != null){
            this.name = updateDataUser.lastname();
        }
    }

    private static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password_plaintext, salt);
    }

    public void disableUser() {
        this.flag = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}