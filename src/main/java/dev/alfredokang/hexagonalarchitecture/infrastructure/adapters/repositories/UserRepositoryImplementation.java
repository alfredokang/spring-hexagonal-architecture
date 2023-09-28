package dev.alfredokang.hexagonalarchitecture.infrastructure.adapters.repositories;

import dev.alfredokang.hexagonalarchitecture.domain.dtos.UserDto;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UsersDto;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;
import dev.alfredokang.hexagonalarchitecture.domain.ports.repositories.UserRepositoryPort;
import dev.alfredokang.hexagonalarchitecture.infrastructure.models.UserModel;
import dev.alfredokang.hexagonalarchitecture.infrastructure.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryImplementation implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserRepositoryImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setName(userDto.getName());
        userModel.setMobile(userDto.getMobile());
        userModel.setEmail(userDto.getEmail());

        UserModel userModelResponse = this.userRepository.save(userModel);

        return new User(
                userModelResponse.getId(),
                userModelResponse.getName(),
                userModelResponse.getMobile(),
                userModelResponse.getEmail(),
                userModelResponse.getCreatedAt()
        );
    }

    @Override
    public UsersDto getUsers(Integer page, Integer pageSizeParam) {
        int defaultPageSize = 10;
        int pageSize = (pageSizeParam != null && pageSizeParam > 0) ? pageSizeParam : defaultPageSize;

        int pageNo = page < 1 ? 0 : page - 1;

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createdAt");
        Page<UserModel> pageUserModel = userRepository.findAll(pageable);

        Page<User> pageUser = pageUserModel.map(userModel -> new User(
                userModel.getId(),
                userModel.getName(),
                userModel.getMobile(),
                userModel.getEmail(),
                userModel.getCreatedAt()
        ));

        return new UsersDto(pageUser);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        Optional<UserModel> userModelOptional = this.userRepository.findById(id);

        Optional<User> userOptional = userModelOptional.map(userModel -> new User(
                userModel.getId(),
                userModel.getName(),
                userModel.getMobile(),
                userModel.getEmail(),
                userModel.getCreatedAt()
        ));

        return userOptional;
    }

    @Override
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User findByIdAndUpdate(String id, UserDto userDto) {
        UUID uuid = UUID.fromString(id);

        UserModel userModel = new UserModel();
        userModel.setId(uuid);
        userModel.setName(userDto.getName());
        userModel.setMobile(userDto.getMobile());
        userModel.setEmail(userDto.getEmail());
        userModel.setCreatedAt(Instant.now());

        UserModel userModelResponse = this.userRepository.save(userModel);

        return new User(
                userModelResponse.getId(),
                userModelResponse.getName(),
                userModelResponse.getMobile(),
                userModelResponse.getEmail(),
                userModelResponse.getCreatedAt()
        );
    }
}
