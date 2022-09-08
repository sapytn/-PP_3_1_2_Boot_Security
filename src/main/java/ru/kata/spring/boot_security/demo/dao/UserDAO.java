package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserDAO  {

  void saveUser(User user);
  void deleteUser(Long id);
  User show(Long id);
  List<User> getUsers();
}
