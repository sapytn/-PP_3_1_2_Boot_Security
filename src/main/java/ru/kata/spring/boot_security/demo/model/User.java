package ru.kata.spring.boot_security.demo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String username;

  private String password;

  private String name;

  private String surname;

  @Column(name = "year")
  private int yearOfBirth;

  @ManyToMany
  @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Collection<Role> roles;

  public void addRole(Role role) {
    this.roles.add(role);
  }

  public User() {

  }

  public User(long id, String username, String password, String name, String surname,
      int yearOfBirth,
      Collection<Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.yearOfBirth = yearOfBirth;
    this.roles = roles;
  }

}
