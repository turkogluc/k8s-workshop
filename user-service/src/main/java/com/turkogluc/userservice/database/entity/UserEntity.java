package com.turkogluc.userservice.database.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "user_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Long amountOfPosts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username)
                && Objects.equals(amountOfPosts, that.amountOfPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, amountOfPosts);
    }
}
