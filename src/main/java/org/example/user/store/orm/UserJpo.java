package org.example.user.store.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.user.aggregate.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_user")
public class UserJpo {
    //시스템 아이디
    @Id
    private String id;

    private String email;
    private String pw;
    private String name;
    private int age;
    @Column(columnDefinition = "DATE")
    private Date time;

    public UserJpo(User user) {
        //
        this.id = user.getId();
        this.email = user.getEmail();
        this.pw = user.getPw();
        this.name = user.getName();
        this.age = user.getAge();
        this.time = user.getTime();
    }

    public User toDomain() {
        //
        return new User(
                this.id,
                this.email,
                this.pw,
                this.name,
                this.age,
                this.time
        );
    }

}
