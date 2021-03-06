package com.alexeykovzel.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Size(min = 1, max = 64)
    private String firstName;

    @Size(max = 64)
    private String lastName;

    @Size(min = 5, max = 32)
    private String username;

    @Override
    public String toString() {
        return String.format("User{firstName='%s', lastName='%s', username='%s'}",
                firstName, lastName, username);
    }
}
