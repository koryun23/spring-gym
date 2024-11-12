package org.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "USER_SUFFIX")
public class UserSuffixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SUFFIX_SEQUENCE")
    @SequenceGenerator(name = "USER_SUFFIX_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "suffix", nullable = false)
    private Long suffix;

    /**
     * Constructor.
     */
    public UserSuffixEntity(String firstName, String lastName, Long suffix) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.suffix = suffix;
    }
}
