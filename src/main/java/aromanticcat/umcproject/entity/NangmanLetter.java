package aromanticcat.umcproject.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NangmanLetter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String senderNickname;

    @NotNull
    private Boolean isPublic;

    @NotNull
    private String content;

    @NotNull
    @Builder.Default
    private Boolean hasResponse = false;

    private Integer thumbsUpCnt;

    private Integer heartCnt;

    private Integer cryingCnt;

    private Integer cloverCnt;

    private Integer clapCnt;

    private Integer starCnt;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(mappedBy = "nangmanLetter", fetch = FetchType.LAZY)
    private NangmanReply nangmanReply;

    public void setHasResponse(boolean hasResponse){
        this.hasResponse = hasResponse;
    }

    public void increaseEmojiCount(String emojiType){
        switch (emojiType) {
            case "thumbsUp":
                thumbsUpCnt = (thumbsUpCnt == null) ? 1 : thumbsUpCnt + 1;
                break;
            case "heart":
                heartCnt = (heartCnt == null) ? 1 : heartCnt + 1;
                break;
            case "cry":
                cryingCnt = (cryingCnt == null) ? 1 : cryingCnt + 1;
                break;
            case "clover":
                cloverCnt = (cloverCnt == null) ? 1 : cloverCnt + 1;
                break;
            case "clap":
                clapCnt = (clapCnt == null) ? 1 : clapCnt + 1;
                break;
            case "star":
                starCnt = (starCnt == null) ? 1 : starCnt + 1;
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 emojiType입니다.");
        }
    }
}
