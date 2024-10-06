package com.rus.nawm.mainservice.controller.graphql;

import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserControllerGraphQL {
  private final UserService userService;

  @MutationMapping
  public User createUser(@Argument("input") @Valid User user) {
    return userService.createUser(user);
  }

  @MutationMapping
  public User updateUser(@Argument("input") @Valid User user) {
    return userService.updateUser(user);
  }

  @QueryMapping
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @QueryMapping
  public User getUser(@Argument("id") String id) {
    return userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}