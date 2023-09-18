package org.example.user.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.user.command.CreateUser;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
public class User {
    //
    private String id;
    private String email;
    private String name;
    private int age;
    private Date time;

    public User() {
        //
        this.time = new Date(System.currentTimeMillis());
    }

    public User(CreateUser command) {
        //
        this();
        this.id = command.getId();
        this.email = command.getEmail();
        this.name = command.getName();
        this.age = command.getAge();
    }
}
