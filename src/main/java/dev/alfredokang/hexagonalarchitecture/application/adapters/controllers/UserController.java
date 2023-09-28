package dev.alfredokang.hexagonalarchitecture.application.adapters.controllers;

import dev.alfredokang.hexagonalarchitecture.application.adapters.dtos.ControllerResponse;
import dev.alfredokang.hexagonalarchitecture.domain.adapters.services.UserServiceImplementation;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UserDto;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UsersDto;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pagesize", defaultValue = "10") Integer pageSize
    ) {
        try {
            UsersDto users = this.userServiceImplementation.getAllUsers(page, pageSize);

            return ResponseEntity.ok(new ControllerResponse("Dados enviados com sucesso!", "success", users));

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ControllerResponse("Ops! Ocorreu um erro inexperado! Tente novamente!", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            Optional<User> user = this.userServiceImplementation.getUserById(uuid);

            if (user.isEmpty()) {
                return new ResponseEntity<>(new ControllerResponse("Usuário não encontrado!", "error"), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(new ControllerResponse("Dado enviado com sucesso!", "success", user));

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ControllerResponse("Ops! Ocorreu um erro inexperado! Tente novamente!", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto userDto) {
        try {
           User user = this.userServiceImplementation.createNewUser(userDto);

           return ResponseEntity.ok(new ControllerResponse("Cadastro realizado com sucesso!", "success", user));

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ControllerResponse("Ops! Ocorreu um erro inexperado! Tente novamente!", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        try {
            UUID uuid = UUID.fromString(id);

            Optional<User> userValidation = this.userServiceImplementation.getUserById(uuid);

            if (userValidation.isEmpty()) {
                return new ResponseEntity<>(new ControllerResponse("Usuário não encontrado!", "error"), HttpStatus.BAD_REQUEST);
            }

            User user = this.userServiceImplementation.findByIdAndUpdate(id, userDto);

            return ResponseEntity.ok(new ControllerResponse("Cadastro atualizado com sucesso!", "success", user));

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ControllerResponse("Ops! Ocorreu um erro inexperado! Tente novamente!", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            Optional<User> user = this.userServiceImplementation.getUserById(uuid);

            if (user.isEmpty()) {
                return new ResponseEntity<>(new ControllerResponse("Usuário não encontrado!", "error"), HttpStatus.BAD_REQUEST);
            }

            this.userServiceImplementation.deleteUserById(uuid);

            return ResponseEntity.ok(new ControllerResponse("Dado deletado com sucesso!", "success"));

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ControllerResponse("Ops! Ocorreu um erro inexperado! Tente novamente!", "error"), HttpStatus.BAD_REQUEST);
        }
    }
}
