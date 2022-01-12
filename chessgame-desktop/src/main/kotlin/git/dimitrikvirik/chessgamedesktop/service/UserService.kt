//package git.dimitrikvirik.chessgamedesktop.service
//
//import git.dimitrikvirik.chessgamedesktop.model.dto.UserAccountDTO
//import git.dimitrikvirik.chessgamedesktop.model.param.UserRegParam
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.http.HttpEntity
//import org.springframework.http.ResponseEntity
//import org.springframework.stereotype.Service
//import org.springframework.web.client.RestTemplate
//
//@Service
//class UserService {
//
//    @Value("\${api.uri}")
//    lateinit var api: String
//
//    val restTemplate = RestTemplate()
//
//    fun registration(userRegParam: UserRegParam) {
//        val entity = restTemplate.postForEntity("http://$api/user", userRegParam, ResponseEntity::class.java)
//        if(entity.statusCode.is2xxSuccessful){
//
//        }
//
//
//
//    }
//
//
//}