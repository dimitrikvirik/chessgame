package git.dimitrikvirik.chessgamedesktop.model

import git.dimitrikvirik.chessgamedesktop.model.domain.User
import org.springframework.stereotype.Component

@Component
class UserContext {

    lateinit var token: String

    lateinit var user: User

}