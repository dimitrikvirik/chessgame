//package git.dimitrikvirik.chessgameapi.service;
//
//import git.dimitrikvirik.chessgameapi.model.domain.UserAccount;
//import lombok.RequiredArgsConstructor;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final KeycloakService keycloakService;
//    private final UserService userService;
//
//    public long getLoggedUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt credentials = (Jwt) authentication.getCredentials();
//        Long database_id = (Long) credentials.getClaims().get("database_id");
//        if (database_id == null) throw new ResponseStatusException(HttpStatus.GONE,
//                "Empty user id to database in authorization token." +
//                        "please contact to administrator!");
//        return database_id;
//    }
//
//    public UserAccount getLoggedUserAccount() {
//        long loggedUserId = getLoggedUserId();
//        return userService.get(loggedUserId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.GONE,
//                        String.format("User with id %d can't be find in database, but was specified in authorization service." +
//                                "please contact to administrator!", loggedUserId))
//        );
//    }
//
//    public UserRepresentation getLoggedUserKeycloak() {
//        return keycloakService.get(getKeycloakId()).toRepresentation();
//    }
//
//    public void logout(String sessionId) {
//        keycloakService.deleteSession(sessionId);
//    }
//
//    public  void logoutAll(String keycloakId, long userId){
//        keycloakService.logout(keycloakId);
//    }
//
//    public String getSessionId() {
//        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        String sessionId = (String) jwt.getTokenAttributes().getOrDefault("session_state", "NOT");
//        if (sessionId.equals("NOT"))
//            throw new ResponseStatusException(HttpStatus.GONE, "Can't find sessionId in token or user already logout");
//        return sessionId;
//    }
//
//    public String getKeycloakId(){
//        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        return jwt.getName();
//    }
//
//}
