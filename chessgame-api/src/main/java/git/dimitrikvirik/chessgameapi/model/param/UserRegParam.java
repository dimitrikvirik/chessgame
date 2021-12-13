package git.dimitrikvirik.chessgameapi.model.param;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Data
@Valid
public class UserRegParam {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
