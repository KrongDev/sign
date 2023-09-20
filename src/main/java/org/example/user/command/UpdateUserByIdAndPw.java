package org.example.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserByIdAndPw {
    //
    private String connectId;
    private String userId;
    private String userPw;
    private String userEmail;
}
