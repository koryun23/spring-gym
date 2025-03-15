package org.example.repository;

import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query("select count(r.id) from UserRoleEntity r where r.role = ?1")
    Integer countOfRole(UserRoleType userRoleType);
}
