package org.example.repository;

import java.util.Optional;
import org.example.entity.user.UserSuffixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSuffixEntityRepository extends JpaRepository<UserSuffixEntity, Long> {

    @Query("select u from UserSuffixEntity u where u.firstName = ?1 and u.lastName = ?2")
    Optional<UserSuffixEntity> findByFirstAndLastName(String firstName, String lastName);
}
