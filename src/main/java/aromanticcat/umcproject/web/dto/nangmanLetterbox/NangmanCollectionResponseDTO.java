package aromanticcat.umcproject.web.dto.nangmanLetterbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class NangmanCollectionResponseDTO {


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PreviewLetterAndReplyResultDTO { // 편지 미리보기 + 답장 미리보기(있을 경우) + 공감 수(공개일 경우)
        private Long nangmanLetterId;

        private Long nangmanReplyId;

        private String previewLetter;

        private String previewReply;

        private Integer totalEmojiCount;

        private LocalDateTime createAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LetterAndReplyResultDTO { // 편지 + 답장 상세 응답
        private Long nangmanLetterId;
        private String letterContent;
        private String senderNickname;
        private Long nangmanReplyId;
        private String replyContent;
        private String replySenderNickname;
    }

}
