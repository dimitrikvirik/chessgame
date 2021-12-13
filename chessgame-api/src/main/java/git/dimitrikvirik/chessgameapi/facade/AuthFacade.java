package git.dimitrikvirik.chessgameapi.facade;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import git.dimitrikvirik.chessgameapi.model.domain.UserAccount;
import git.dimitrikvirik.chessgameapi.model.dto.UserAccountDTO;
import git.dimitrikvirik.chessgameapi.model.dto.UserDTO;
import git.dimitrikvirik.chessgameapi.model.mapper.KeycloakMapper;
import git.dimitrikvirik.chessgameapi.model.param.UserRegParam;
import git.dimitrikvirik.chessgameapi.service.KeycloakService;
import git.dimitrikvirik.chessgameapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;
    private final KeycloakService keycloakService;


    public void registration( UserRegParam param) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(param.getUsername());
        userAccount.setEmail(param.getEmail());
        UserAccount save = this.userService.save(userAccount);
        KeycloakService keycloakService = this.keycloakService;
        UserRepresentation userRepresentation = keycloakService.create(
                KeycloakMapper.toRepresentation(param.getUsername(), param.getEmail(), param.getPassword(), save.getId())
        );
        save.setKeycloakId(userRepresentation.getId());
        this.userService.save(save);
        this.keycloakService.sendVerifyEmail(userRepresentation.getId());
    }


    public UserDTO login(String username, String password) {
        try {
            HttpResponse<String> login = this.keycloakService.login(username, password);
            if (login.getStatus() == 401) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong phone or password!");
            } else {
                UserAccount userAccount = userService.getByUsername(username).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.GONE, "User was authorized but can't find in database. please contact to administration!"));
                return new UserDTO(new UserAccountDTO(userAccount), login.getBody());
            }
        } catch (UnirestException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error! please try again or contact to administration!");
        }
    }
}
