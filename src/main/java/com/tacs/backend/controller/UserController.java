package com.tacs.backend.controller;

import com.tacs.backend.dto.ApiErrorDTO;
import com.tacs.backend.dto.CreationDTO;
import com.tacs.backend.model.User;
import com.tacs.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired UserService userService;

  @Operation(summary = "Get all users")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Found the users"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @GetMapping()
  public Iterable<User> getUsers() {
    return userService.getUsers();
  }

  @Operation(summary = "Get a user by its id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user",
            content =
                @Content(
                    schema = @Schema(implementation = User.class),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content()),
        @ApiResponse(responseCode = "404", description = "user not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @GetMapping("/{id}")
  public User getUser(@PathVariable String id) {
    return userService.getUser(id);
  }

  @Operation(summary = "Create a new user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid body",
            content =
                @Content(
                    schema = @Schema(implementation = ApiErrorDTO.class),
                    mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @PostMapping()
  public CreationDTO createUser(@Valid @RequestBody User user) {
    String id = userService.createUser(user);

    return new CreationDTO(id);
  }

  @Operation(summary = "Delete a user by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Deleted the user", content = @Content),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content =
                @Content(
                    schema = @Schema(implementation = ApiErrorDTO.class),
                    mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
  }
}
