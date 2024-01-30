package aromanticcat.umcproject.web.dto.nangmanLetterbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class  NangmanLetterBoxResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendLetterResultDTO { // 편지 발송 후 응답

        private Long nangmanLetterId;

        private String senderNickname;

        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PreviewLetterResultDTO { // 편지 미리보기
        private Long nangmanLetterId;

        private String preview;

        private LocalDateTime createdAt;
    }




    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectedLetterResultDTO { // 편지 선택 후 응답
        private Long nangmanLetterId;
        private String senderNickname;
        private String nangmanLetterContent;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendReplyResultDTO { // 답장 발송 완료 응답
        private Long nangmanLetterId;
        private Long nangmanReplyId;
        private String replySenderNickname;
        LocalDateTime createdAt;
    }


}
