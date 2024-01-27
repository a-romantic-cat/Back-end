package aromanticcat.umcproject.web.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class StoreResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LetterPaperResultDTO {

        private Long letterPaperId;

        private String letterPaperImageUrl;

        private String letterPaperName;

        private boolean isAcquired;

        private Integer price;

        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StampResultDTO {

        private Long stampId;

        private String stampImageUrl;

        private String stampName;

        private boolean isAcquired;

        private LocalDateTime createdAt;
    }
}
