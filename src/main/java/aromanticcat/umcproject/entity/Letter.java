package aromanticcat.umcproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "letter")
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letter_id;

    @Column(name = "nickname", length = 60)
    private String nickname;

    @Column(length = 255)
    private String content;

    private Boolean open;

    @Column(name = "login_status")
    private Boolean loginStatus;

    @Column(name = "sender_ID")
    private Long senderId;

    @ManyToOne
    @JoinColumn(name = "letter_paper_id",insertable = false, updatable = false)
    private LetterPaper letterPaper;

    @ManyToOne
    @JoinColumn(name = "stamp_id", insertable = false, updatable = false)
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Letterbox letterbox;
}
