package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPassword(String password);

    List<UserEntity> findAllByUsernameContains(String pattern);

    @Query("update UserEntity u set u.password = ?2 where u.username = ?1")
    UserEntity changePassword(String username, String newPassword);

    @Query("update UserEntity u set u.is_active = !u.is_active where u.username = ?1")
    UserEntity switchActivationState(String username);

    @Query("update UserEntity u set u.firstName = ?2, u.lastName = ?3, u.isActive = ?4 where u.username = ?1")
    UserEntity update(String username, String firstName, String lastName, Boolean isActive);
}
