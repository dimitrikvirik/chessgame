//package git.dimitrikvirik.chessgameapi.controller;
//
//import git.dimitrikvirik.chessgameapi.facade.AuthFacade;
//import git.dimitrikvirik.chessgameapi.model.param.UserRegParam;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//    private final AuthFacade authFacade;
//
//    @PostMapping
//    public void registration(@Valid @RequestBody  UserRegParam userRegParam) {
//        authFacade.registration(userRegParam);
//    }
//}
