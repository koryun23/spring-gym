package org.example.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "USER_ROLE")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence")
    @SequenceGenerator(name = "user_role_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserEntity user;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleType role;

    /**
     * Constructor.
     */
    public UserRoleEntity(UserEntity user, UserRoleType role) {
        this.user = user;
        this.role = role;
    }
}
