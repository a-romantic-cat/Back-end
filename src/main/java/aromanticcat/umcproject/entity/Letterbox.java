package aromanticcat.umcproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "letterbox")
public class Letterbox extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letterbox_id;

    @NotNull
    private String name;
    @NotNull
    private String color;

    @NotNull
    @Column(name = "end_dt")
    private LocalDateTime endDt;

    @NotNull
    private Boolean activate;

    @NotNull
    private Boolean sender;

    @OneToMany(mappedBy = "letterbox")
    private List<Letter> letters;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Letterbox(String name, String color, LocalDateTime endDt, Boolean activate, Boolean sender) {
        this.name = name;
        this.color = color;
        this.endDt = endDt;
        this.activate = activate;
        this.sender = sender;
    }
}
