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
    private String userCode;
    private String connectId;

    private String userId;
    private String userPw;

    private String email;
    private String name;
    private Date createAt;
    private Date updateAt;

    public User() {
        //
        this.createAt = new Date(System.currentTimeMillis());
        this.updateAt = new Date(System.currentTimeMillis());
    }

    public User(CreateUser command) {
        //
        this();
        this.userCode = command.getUserCode();
        this.connectId = command.getConnectId();
        this.name = command.getUserName();
    }
}
