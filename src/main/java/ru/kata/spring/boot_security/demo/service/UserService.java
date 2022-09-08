package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService extends UserDetailsService {

  void saveUser(User user);

  @Transactional
  void updateUser(User user);

  void deleteUser(Long id);

  List<User> getUsers();

  User showUser(Long id);

}
