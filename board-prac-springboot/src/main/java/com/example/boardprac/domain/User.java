package com.example.boardprac.domain;

import com.example.boardprac.dto.UserDto;
import com.example.boardprac.global.Role;
import com.example.boardprac.global.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> heartList = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @Builder
    public User(String email, String name, String password, Role role, UserStatus status) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDto toUserDto() {
        return UserDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .role(this.role)
                .status(this.status)
                .build();
    }

    public boolean isNotAdmin() {
        return !this.role.equals(Role.ROLE_ADMIN);
    }

    public boolean canNotControlUser(User target) {
        return this.id != target.getId() && this.role != Role.ROLE_ADMIN;
    }

    public boolean canNotControlPost(Post target) {
        return !this.equals(target.getUser()) && this.role != Role.ROLE_ADMIN;
    }

    public void toBannedUser(UserStatus status) {
        this.status = status;
    }

    public void toLeavedUser(UserStatus status) {
        this.status = status;
    }
}
