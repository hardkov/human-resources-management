package com.agh.hr.persistence.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // user's email -> data redundancy
    @Column(unique = true)
    private String username;

    private String passwordHash;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> authorities;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "permissions_id")
    private Permission permissions;

    private String position;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Leave> leaves;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bonus> bonuses;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Delegation> delegations;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Application> applications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
