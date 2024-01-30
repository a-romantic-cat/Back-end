package aromanticcat.umcproject.web.dto.nangmanLetterbox;

import lombok.Getter;

public class NangmanLetterboxRequestDTO {

    @Getter
    public static class SendLetterDTO {

        private Boolean isPublic;
        private String content;
        private String senderRandomNickname;
    }

    @Getter
    public static class SendReplyDTO {

        private String replySenderNickname;
        private String replyContent;
    }
}
