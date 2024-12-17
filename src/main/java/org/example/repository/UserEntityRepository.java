package org.example.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.example.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.password = ?2 where u.username = ?1")
    void updatePassword(String username, String newPassword);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.firstName = ?2, u.lastName = ?3, u.isActive = ?4 where u.username = ?1")
    void update(String username, String firstName, String lastName, Boolean isActive);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.isActive = ?2 where u.username = ?1")
    void switchActivationState(String username, Boolean isActive);
}
