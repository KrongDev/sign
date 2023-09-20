package org.example.user.store.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shared.DomainEntity;
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
public class UserJpo implements DomainEntity<User> {
    //시스템 아이디
    @Id
    private String userCode;
    private String connectId;

    private String userId;
    private String userPw;

    private String email;
    private String name;
    @Column(columnDefinition = "DATE")
    private Date createAt;
    @Column(columnDefinition = "DATE")
    private Date updateAt;

    public UserJpo(User user) {
        //
        this.userCode = user.getUserCode();
        this.connectId = user.getConnectId();
        this.userId = user.getUserId();
        this.userPw = user.getUserPw();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
    }

    @Override
    public User toDomain() {
        //
        return new User(
                this.userCode,
                this.connectId,
                this.userId,
                this.userPw,
                this.email,
                this.name,
                this.createAt,
                this.updateAt
        );
    }

}
