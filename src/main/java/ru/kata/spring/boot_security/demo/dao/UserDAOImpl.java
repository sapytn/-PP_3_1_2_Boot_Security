package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public class UserDAOImpl implements UserDAO{

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void saveUser(User user) {
    User newUser = entityManager.merge(user);
    user.setId(newUser.getId());
  }

  @Override
  public void deleteUser(Long id) {
    User user = entityManager.find(User.class, id);
    entityManager.remove(user);
  }

  @Override
  public User show(Long id) {
    return entityManager.find(User.class, id);
  }


  @Override
  public List<User> getUsers() {
    TypedQuery<User> query = entityManager.createQuery("select user from User user", User.class);
    return query.getResultList();
  }
}
