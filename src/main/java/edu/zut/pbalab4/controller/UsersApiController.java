package edu.zut.pbalab4.controller;

import edu.zut.pbalab4.api.UsersApi;
import edu.zut.pbalab4.model.CreateRequest;
import edu.zut.pbalab4.model.RequestHeader;
import edu.zut.pbalab4.model.UpdateRequest;
import edu.zut.pbalab4.model.User;
import edu.zut.pbalab4.model.UserListResponse;
import edu.zut.pbalab4.model.UserResponse;
import edu.zut.pbalab4.service.JwsService;
import edu.zut.pbalab4.service.UserService;
import edu.zut.pbalab4.exception.InvalidSignatureException;
import edu.zut.pbalab4.service.HmacService;
import edu.zut.pbalab4.exception.InvalidJwsSignatureException;
import edu.zut.pbalab4.service.JwsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class UsersApiController implements UsersApi {

    private final UserService userService;
    private final HmacService hmacService;
    private final JwsService jwsService;

    public UsersApiController(UserService userService, HmacService hmacService, JwsService jwsService) {
        this.userService = userService;
        this.hmacService = hmacService;
        this.jwsService = jwsService;
    }

    @Override
    public ResponseEntity<UserResponse> createUser(String X_HMAC_SIGNATURE, CreateRequest body) {
        if (!hmacService.isValidCreateRequestSignature(body, X_HMAC_SIGNATURE)) {
            throw new InvalidSignatureException();
        }

        User createdUser = userService.create(body);

        UserResponse response = new UserResponse();
        response.setResponseHeader(buildRequestHeader(body.getRequestHeader() != null
                ? body.getRequestHeader().getRequestId()
                : UUID.randomUUID()));
        response.setUser(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserListResponse> getAllUsers() {
        List<User> users = userService.getAll();

        UserListResponse response = new UserListResponse();
        response.setResponseHeader(buildRequestHeader(UUID.randomUUID()));
        response.setUsersList(users);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(UUID id) {
        User user = userService.getOne(id);

        UserResponse response = new UserResponse();
        response.setResponseHeader(buildRequestHeader(UUID.randomUUID()));
        response.setUser(user);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UUID id, String X_JWS_SIGNATURE, UpdateRequest body) {
        if (!jwsService.isValidJwsForUpdateRequest(body, X_JWS_SIGNATURE)) {
            throw new InvalidJwsSignatureException();
        }

        User updatedUser = userService.update(id, body);

        UserResponse response = new UserResponse();
        response.setResponseHeader(buildRequestHeader(body.getRequestHeader() != null
                ? body.getRequestHeader().getRequestId()
                : UUID.randomUUID()));
        response.setUser(updatedUser);

        return ResponseEntity.ok(response);
    }

    private RequestHeader buildRequestHeader(UUID requestId) {
        RequestHeader header = new RequestHeader();
        header.setRequestId(requestId);
        header.setSendDate(OffsetDateTime.now());
        return header;
    }
}