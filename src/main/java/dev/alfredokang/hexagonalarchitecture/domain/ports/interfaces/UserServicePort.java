package dev.alfredokang.hexagonalarchitecture.domain.ports.interfaces;

import dev.alfredokang.hexagonalarchitecture.domain.dtos.UserDto;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UsersDto;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {
    User createNewUser(UserDto userDto);
    UsersDto getAllUsers(Integer page, Integer pageSize);
    Optional<User> getUserById(UUID id);
    void deleteUserById(UUID id);
    User findByIdAndUpdate(String id, UserDto userDto);
}
