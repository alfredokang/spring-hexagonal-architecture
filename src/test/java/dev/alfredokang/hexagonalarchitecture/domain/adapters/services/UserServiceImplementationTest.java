package dev.alfredokang.hexagonalarchitecture.domain.adapters.services;

import dev.alfredokang.hexagonalarchitecture.domain.dtos.UserDto;
import dev.alfredokang.hexagonalarchitecture.domain.dtos.UsersDto;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;
import dev.alfredokang.hexagonalarchitecture.domain.ports.repositories.UserRepositoryPort;
import dev.alfredokang.hexagonalarchitecture.infrastructure.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @InjectMocks
    UserServiceImplementation service;

    @Mock
    UserRepositoryPort repositoryPort;

    User user;
    UserDto userDto;
    UUID uuid;
    Instant instant;
    Optional<User> userOptional;

    @BeforeEach
    public void setup() {
        uuid = UUID.randomUUID();
        instant = Instant.now();

        userDto = new UserDto(
                "Alfredo Kang",
                "(11) 99999-1000",
                "alfredok@icloud.com"
        );

        user = new User(
                uuid,
                "Alfredo Kang",
                "(11) 99999-1000",
                "alfredok@icloud.com",
                instant
        );

        userOptional = Optional.of(new User(
                uuid,
                "Alfredo Kang",
                "(11) 99999-1000",
                "alfredok@icloud.com",
                instant
        ));
    }

    @Test
    void deveRetornarUsuariosCadastradoENaoDeveRetornarNull() {
        List<UserModel> userList = new ArrayList<>();
        userList.add(new UserModel(
                uuid,
                "Alfredo Kang",
                "(11) 99999-1000",
                "alfredok@icloud.com",
                instant
                )
        );

        userList.add(new UserModel(
                uuid,
                "Simon Kang",
                "(11) 99999-1000",
                "simon@icloud.com",
                instant
                )
        );

        int pageNo = 0;
        int pageSize = 10;

        Sort.Direction direction = Sort.Direction.DESC;
        String orderBy = "createdAt";

        Pageable pageable = PageRequest.of(pageNo, pageSize, direction, orderBy);

        Page<UserModel> pageUserModel = new PageImpl<>(userList, pageable, userList.size());

        Page<User> pageUser = pageUserModel.map(userModel -> new User(
                userModel.getId(),
                userModel.getName(),
                userModel.getMobile(),
                userModel.getEmail(),
                userModel.getCreatedAt()
        ));

        when(repositoryPort.getUsers(1, 10)).thenReturn(new UsersDto(pageUser));

        var response = service.getAllUsers(1,10);

        assertNotNull(response);
        assertEquals(user.getName(), response.getUsers().get(0).getName());
        verify(repositoryPort).getUsers(1,10);
        verifyNoMoreInteractions(repositoryPort);
    }

    @Test
    void deveCadastrarUsuarioRetornarCadastrado() {
        when(repositoryPort.saveUser(userDto)).thenReturn(user);

        var response = assertDoesNotThrow(() -> service.createNewUser(userDto));

        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
        verify(repositoryPort).saveUser(userDto);
        verifyNoMoreInteractions(repositoryPort);
    }

    @Test
    void deveRetornarUsuarioSelecionado() {
        when(repositoryPort.getUserById(uuid)).thenReturn(userOptional);

        var response = service.getUserById(uuid);

        assertNotNull(response);
        assertEquals(user.getName(), response.get().getName());
    }

    @Test
    void deveRetornarEmptyCasoUsuarioNaoEncontrado() {
        String uuidStringNotExist = "550e8400-e29b-41d4-a716-446655440000";
        UUID uuid2 = UUID.fromString(uuidStringNotExist);

        when(repositoryPort.getUserById(uuid2)).thenReturn(null);

        var responseError = service.getUserById(uuid2);

        assertNull(responseError);
    }

    @Test
    void deveRetornarUsuarioAlterado() {
        String uuidString = uuid.toString();

        when(repositoryPort.findByIdAndUpdate(uuidString, userDto)).thenReturn(user);

        var response = service.findByIdAndUpdate(uuidString, userDto);

        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
    }

    @Test
    void deveRetornarNullSeUsuarioNaoEncontrado() {
        String uuidStringNotExist = "550e8400-e29b-41d4-a716-446655440000";

        String uuidString = uuid.toString();

        when(repositoryPort.findByIdAndUpdate(uuidStringNotExist, userDto)).thenReturn(null);

        var responseError = service.findByIdAndUpdate(uuidStringNotExist, userDto);

        assertNull(responseError);
    }

    @Test
    void naoDeveRetornarErroQuandoUsuarioDeletado() {
        doNothing().when(repositoryPort).deleteUserById(uuid);

        service.deleteUserById(uuid);

        verify(repositoryPort, times(1)).deleteUserById(uuid);

    }

    @Test
    void deveRetornarErroQuandoIdForInvalido() {
        String uuidStringNotExist = "550e8400-e29b-41d4-a716-446655440000";
        UUID uuid2 = UUID.fromString(uuidStringNotExist);

        doThrow(new RuntimeException("Erro de exclusão")).when(repositoryPort).deleteUserById(uuid2);

        try {
            service.deleteUserById(uuid2);
            fail("Deveria ter lançado uma exceção");
        } catch (RuntimeException e) {
            assertEquals("Erro de exclusão", e.getMessage());
        }

        verify(repositoryPort, times(1)).deleteUserById(uuid2);
    }
}