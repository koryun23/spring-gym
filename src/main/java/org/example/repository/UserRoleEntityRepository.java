package org.example.repository;

import java.util.List;
import org.example.entity.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query("select r from UserRoleEntity r join UserEntity u on r.user.id = u.id where u.username = ?1")
    List<UserRoleEntity> findAllByUserUsername(String username);

    @Query("delete from UserRoleEntity r join UserEntity u on r.user.id = u.id where u.username = ?1")
    void deleteAllByUserUsername(String username);
}
