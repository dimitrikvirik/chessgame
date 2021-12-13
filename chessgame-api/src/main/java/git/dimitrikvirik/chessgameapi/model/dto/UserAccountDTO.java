package git.dimitrikvirik.chessgameapi.model.dto;

import git.dimitrikvirik.chessgameapi.model.domain.UserAccount;
import lombok.Data;

@Data
public  class UserAccountDTO {
    private Long id;

    private String username;

    private String email;

    private String keycloakId;



    public UserAccountDTO(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.username = userAccount.getUsername();
        this.email = userAccount.getEmail();
        this.keycloakId =  userAccount.getKeycloakId();
    }


}