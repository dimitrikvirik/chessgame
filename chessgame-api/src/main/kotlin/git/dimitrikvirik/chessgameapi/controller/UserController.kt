package git.dimitrikvirik.chessgameapi.controller

import git.dimitrikvirik.chessgameapi.model.param.UserParam
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController {

    @PostMapping("/registration")
    fun registration(@Valid @RequestBody userParam: UserParam) {

    }

}