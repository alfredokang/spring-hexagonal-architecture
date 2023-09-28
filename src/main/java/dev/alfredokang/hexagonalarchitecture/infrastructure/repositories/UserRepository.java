package dev.alfredokang.hexagonalarchitecture.infrastructure.repositories;

import dev.alfredokang.hexagonalarchitecture.infrastructure.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
