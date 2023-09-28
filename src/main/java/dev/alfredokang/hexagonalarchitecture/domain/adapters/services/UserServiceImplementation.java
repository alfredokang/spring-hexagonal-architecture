package dev.alfredokang.hexagonalarchitecture.domain.adapters.services;

import dev.alfredokang.hexagonalarchitecture.domain.dtos.UserDto;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UsersDto;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;
import dev.alfredokang.hexagonalarchitecture.domain.ports.interfaces.UserServicePort;
import dev.alfredokang.hexagonalarchitecture.domain.ports.repositories.UserRepositoryPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserServiceImplementation implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;

    public UserServiceImplementation(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createNewUser(UserDto userDto) {
        return this.userRepositoryPort.saveUser(userDto);
    }

    @Override
    public UsersDto getAllUsers(Integer page, Integer pageSize) {
        log.info("Estou na página {}", page);
        return this.userRepositoryPort.getUsers(page, pageSize);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return this.userRepositoryPort.getUserById(id);
    }

    @Override
    public void deleteUserById(UUID id) {
        this.userRepositoryPort.deleteUserById(id);
    }

    @Override
    public User findByIdAndUpdate(String id, UserDto userDto) {
        return this.userRepositoryPort.findByIdAndUpdate(id, userDto);
    }
}
