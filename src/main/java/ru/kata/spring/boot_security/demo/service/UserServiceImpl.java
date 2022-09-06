package ru.kata.spring.boot_security.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  private PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Transactional
  @Override
  public void saveUser(User user) {
    user.setPassword(passwordEncoder().encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional
  @Override
  public void deleteUser(Long id) {
    userDAO.deleteUser(id);
  }

  @Transactional
  @Override
  public List<User> getUsers() {
    return userDAO.getUsers();
  }

  @Transactional
  @Override
  public User show(Long id) {
    return userDAO.show(id);
  }

  @Transactional
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByUsername(username);
    if(user == null) {
      throw new UsernameNotFoundException(String.format("User '%s' not found", username));
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), mapRolesAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }

  @Transactional
  public List<Role> listRoles() {
    return roleRepository.findAll();
  }
}
