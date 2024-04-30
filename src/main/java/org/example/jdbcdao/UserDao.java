package org.example.jdbcdao;

import org.example.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById (String id);
    Optional<User> findById2 (String id);

    User save (User user);
    void update (User user);
    void deleteById(String id);

    void saveUserFile (String id, byte[] files);

}
