package git.dimitrikvirik.chessgameapi.model.param

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class UserParam(
    @NotBlank @Max(100) val username: String,
    @NotBlank val password: String
)