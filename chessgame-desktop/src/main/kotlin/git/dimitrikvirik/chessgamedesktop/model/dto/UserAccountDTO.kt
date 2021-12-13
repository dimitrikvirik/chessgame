package git.dimitrikvirik.chessgamedesktop.model.dto



data class UserAccountDTO(
     val id: Long,
     val username: String,
     val email: String,
     val keycloakId: String,
) {


}