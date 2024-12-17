package org.example.repository;

import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;
import org.example.entity.trainee.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository extends JpaRepository<TraineeEntity, Long> {

    Optional<TraineeEntity> findByUserUsername(String username);

    @Transactional
    void deleteByUserUsername(String username);

    @Modifying
    @Transactional
    @Query("update TraineeEntity t set t.dateOfBirth = ?2, t.address = ?3 where t.user.username = ?1")
    void update(String username, Date dateOfBirth, String address);
}
