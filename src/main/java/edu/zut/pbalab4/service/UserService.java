package edu.zut.pbalab4.service;

import edu.zut.pbalab4.exception.UserAlreadyExistsException;
import edu.zut.pbalab4.exception.UserNotFoundException;
import edu.zut.pbalab4.model.CreateRequest;
import edu.zut.pbalab4.model.UpdateRequest;
import edu.zut.pbalab4.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public User create(CreateRequest request) {
        User user = request.getUser();

        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }

        boolean personalIdExists = users.values().stream()
                .anyMatch(existing -> Objects.equals(existing.getPersonalId(), user.getPersonalId()));

        if (personalIdExists) {
            throw new UserAlreadyExistsException(user.getPersonalId());
        }

        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getOne(UUID id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public User update(UUID id, UpdateRequest request) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException(id);
        }

        User updatedUser = request.getUser();
        updatedUser.setId(id);

        boolean personalIdExistsForOtherUser = users.values().stream()
                .anyMatch(existing ->
                        !existing.getId().equals(id) &&
                                Objects.equals(existing.getPersonalId(), updatedUser.getPersonalId()));

        if (personalIdExistsForOtherUser) {
            throw new UserAlreadyExistsException(updatedUser.getPersonalId());
        }

        users.put(id, updatedUser);
        return updatedUser;
    }

    public void delete(UUID id) {
        User removed = users.remove(id);
        if (removed == null) {
            throw new UserNotFoundException(id);
        }
    }
}