package com.userManagement.userManagement.service;


import com.userManagement.userManagement.dto.UserLoginRequestDTO;
import com.userManagement.userManagement.dto.UserRegisterDTO;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    public void createKeycloakUser(UserRegisterDTO request, String roleName) {
        // Use the master realm to authenticate as admin
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .username(adminUsername)
                .password(adminPassword)
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        // Create new user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);

        // Create the user in target realm
        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }

        System.out.println("Response status: " + response.getStatus());
        System.out.println("Location header: " + response.getLocation());

        // Extract created user's ID
        URI location = response.getLocation();
        if (location == null) {
            throw new RuntimeException("User created, but location header is missing. Cannot extract userId.");
        }
        String userId = location.getPath().replaceAll(".*/([^/]+)$", "$1");

        // Set password
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(request.getPasswordHash());
        keycloak.realm(realm).users().get(userId).resetPassword(cred);

        // Assign client-level role
        // 1. Get client UUID
        List<ClientRepresentation> clients = keycloak.realm(realm).clients().findByClientId(clientId);
        if (clients.isEmpty()) {
            throw new RuntimeException("No client found with clientId: " + clientId + " in realm: " + realm);
        }

        ClientRepresentation client = clients.get(0);
        System.out.println("Client ID: " + client.getId());
        System.out.println("Client Name: " + client.getClientId());
        String clientUuid = client.getId();

        // 2. Get role by name from that client
        RoleRepresentation clientRole = keycloak.realm(realm)
                .clients().get(clientUuid)
                .roles().get(roleName)
                .toRepresentation();

        System.out.println("Client ID: " + clientUuid);
        System.out.println("Client Name: " + client.getClientId());


        // 3. Assign role to the user at client level
        keycloak.realm(realm)
                .users().get(userId)
                .roles()
                .clientLevel(clientUuid)
                .add(Collections.singletonList(clientRole));
    }

    public ResponseEntity<?> Login(UserLoginRequestDTO loginDTO) {
        try {
            String tokenEndpoint = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("grant_type", "password");
            form.add("client_id", "spring-cloud-client");
            form.add("client_secret", "KWq14DjOtVX95gRW3zkVlg3L4kWpQjaq");
            form.add("username", loginDTO.getEmail());
            form.add("password", loginDTO.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
}

