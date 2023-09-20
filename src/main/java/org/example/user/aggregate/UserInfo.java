package org.example.user.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    //
    private String name;
    private String connectId;

    public UserInfo(User user) {
        //
        this.name = user.getName();
        this.connectId = user.getConnectId();
    }
}
