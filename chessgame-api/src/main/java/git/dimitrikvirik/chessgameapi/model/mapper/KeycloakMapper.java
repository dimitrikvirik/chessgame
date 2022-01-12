//package git.dimitrikvirik.chessgameapi.model.mapper;
//
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class KeycloakMapper {
//
//    public static UserRepresentation toRepresentation(String username, String email, String password, Long databaseId) {
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setUsername(username);
//        userRepresentation.setEmail(email);
//        userRepresentation.setEnabled(true);
//
//        Map<String, List<String>> attributes = new HashMap<>();
//        attributes.put("database_id", Collections.singletonList(databaseId.toString()));
//        userRepresentation.setAttributes(attributes);
//
//
//        userRepresentation.setCredentials(Collections.singletonList(toCredential(password)));
//
//        return userRepresentation;
//
//    }
//
//    public static CredentialRepresentation toCredential(String password) {
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//        credentialRepresentation.setValue(password);
//        credentialRepresentation.setTemporary(false);
//        return credentialRepresentation;
//
//    }
//}
