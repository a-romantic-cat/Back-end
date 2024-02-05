package aromanticcat.umcproject.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "myLetterPaper")
public class MyLetterPaper extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mPaper_id;

    @NotNull
    private String imageUrl;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MyLetterPaper(Long id, String imageUrl, String name, Member member) {
        this.mPaper_id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.member = member;
    }

}
