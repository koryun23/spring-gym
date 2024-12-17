package org.example.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "LOGIN_ATTEMPT")
public class LoginAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGIN_ATTEMPT_SEQUENCE")
    @SequenceGenerator(name = "LOGIN_ATTEMPT_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "remote_address", nullable = false)
    private String remoteAddress;

    @Column(name = "counter", nullable = false)
    private Integer counter = 0;

    @Column(name = "blocked_until", nullable = false)
    private LocalDateTime blockedUntil = LocalDateTime.MIN;

    /**
     * Constructor.
     */
    public LoginAttemptEntity(String remoteAddress, Integer counter, LocalDateTime blockedUntil) {
        this.remoteAddress = remoteAddress;
        this.counter = counter;
        this.blockedUntil = blockedUntil;
    }
}
