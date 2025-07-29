package beyond.ordersystem.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
// jpql을 제외하고 모든 조회 쿼리에 where del_yn = "N" 붙이는 효과
@Where(clause = "del_yn = 'N'")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    private String password;

    @Builder.Default
    private String delYn = "N";

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    public void deleteMember(String delYn) {
        this.delYn = delYn;
    }
}
