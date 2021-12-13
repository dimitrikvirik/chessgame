package git.dimitrikvirik.chessgameapi.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * @author dito
 */
@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${common.keycloak.token-uri}")
    private String tokenUri;
    @Value("${common.keycloak.client-id}")
    private String clientId;
    @Value("${common.keycloak.client-secret}")
    private String clientSecret;

    private final Keycloak keycloak;


    public UserRepresentation create(UserRepresentation userRepresentation) {
        keycloak.realm(realm).users().create(userRepresentation);
        return keycloak.realm(realm).users().search(userRepresentation.getEmail()).get(0);
    }

    public void update(UserRepresentation userRepresentation) {
        keycloak.realm(realm).users().get(userRepresentation.getId()).update(userRepresentation);
    }
    public void resetPassword(String keycloakId, CredentialRepresentation credentialRepresentation){
        keycloak.realm(realm).users().get(keycloakId).resetPassword(credentialRepresentation);
    }

    public UserResource get(String keycloakId){
       return keycloak.realm(realm).users().get(keycloakId);
    }



    public UserSessionRepresentation getSession(String keycloakId, String sessionId){



       return keycloak.realm(realm).users().get(keycloakId).getUserSessions().stream().filter((u -> u.getId().equals(sessionId))).findFirst().orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Session not found!")
        );
    }

    public void deleteSession(String sessionId){
        keycloak.realm(realm).deleteSession(sessionId);
    }

    public void logout(String keycloakId){
        keycloak.realm(realm).users().get(keycloakId).logout();
    }

    public HttpResponse<String> login(String username, String password) throws UnirestException {
        Unirest.setTimeouts(0, 0);
       return Unirest.post(tokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("username", username)
                .field("password", password)
                .field("client_id", clientId)
                .field("grant_type", "password")
                .field("client_secret", clientSecret)
                .field("scope", "offline_access profile")
                .asString();
    }
    public HttpResponse<String> reLogin(String refresh_token) throws UnirestException {
        Unirest.setTimeouts(0, 0);
         return  Unirest.post(tokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", clientId)
                .field("client_secret", clientSecret)
                .field("grant_type", "refresh_token")
                .field("refresh_token", refresh_token)
                .asString();
    }


    public void sendVerifyEmail(String keycloakId){
        keycloak.realm(realm).users().get(keycloakId).sendVerifyEmail();
    }


}
