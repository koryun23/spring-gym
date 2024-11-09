package org.example.repository;

import java.sql.Date;
import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository extends JpaRepository<TraineeEntity, Long> {

    Optional<TraineeEntity> findByUserUsername(String username);

    void deleteByUserUsername(String username);

    @Query("update TraineeEntity t set t.dateOfBirth = ?2, t.address = ?3 where t.user.username = ?1")
    TraineeEntity update(String username, Date dateOfBirth, String address);
}
