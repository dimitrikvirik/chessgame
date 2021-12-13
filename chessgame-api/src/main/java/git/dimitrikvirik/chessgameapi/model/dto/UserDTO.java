package git.dimitrikvirik.chessgameapi.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private UserAccountDTO model;
    private String token;

}

