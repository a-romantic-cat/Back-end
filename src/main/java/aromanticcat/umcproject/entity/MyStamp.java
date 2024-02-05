package aromanticcat.umcproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@Table(name = "myStamp")
public class MyStamp extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mStamp_id;

    @NotNull
    private String imageUrl;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MyStamp(Long id, String imageUrl, String name, Member member) {
        this.mStamp_id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.member = member;
    }
}
