package aromanticcat.umcproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcquiredItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "letter_paper_id")
    private LetterPaper letterPaper;

    @ManyToOne
    @JoinColumn(name = "stamp_id")
    private Stamp stamp;
}
