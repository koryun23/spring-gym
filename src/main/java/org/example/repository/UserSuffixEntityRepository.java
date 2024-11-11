package org.example.repository;

import java.util.Optional;
import org.example.entity.UserSuffixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSuffixEntityRepository extends JpaRepository<UserSuffixEntity, Long> {

    @Query("select u from UserSuffixEntity u where u.firstName = ?1 and u.lastName = ?2")
    Optional<UserSuffixEntity> findByFirstAndLastName(String firstName, String lastName);

    @Query("update UserSuffixEntity u set u.suffix = u.suffix + 1")
    UserSuffixEntity updateSuffix(String firstName, String lastName);
}
