package dev.alfredokang.hexagonalarchitecture.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.alfredokang.hexagonalarchitecture.domain.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class UsersDto {
    private long totalElements;
    private int totalPages;
    private int currentPage;

    @JsonProperty("isFirst")
    private boolean isFirst;

    @JsonProperty("isLast")
    private boolean isLast;

    private boolean hasNext;
    private boolean hasPrevious;

    private List<User> users;

    public UsersDto(Page<User> users) {
        this.setUsers(users.getContent());
        this.setTotalElements(users.getTotalElements());
        this.setTotalPages(users.getTotalPages());
        this.setCurrentPage(users.getNumber() + 1);
        this.setFirst(users.isFirst());
        this.setLast(users.isLast());
        this.setHasNext(users.hasNext());
        this.setHasPrevious(users.hasPrevious());
    }
}
