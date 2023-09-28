package dev.alfredokang.hexagonalarchitecture.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    UUID id;
    String name;
    String mobile;
    String email;
    Instant createdAt;
}
