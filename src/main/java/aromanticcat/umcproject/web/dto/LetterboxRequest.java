package aromanticcat.umcproject.web.dto;

import aromanticcat.umcproject.entity.Letterbox;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LetterboxRequest {
    private String name;
    private String color;
    private LocalDateTime endDt;
    private Boolean activate;
    private Boolean sender;

    private Long member_id;
    public Letterbox toEntity() {
        Letterbox letterbox = Letterbox.builder()
                .name(name)
                .color(color)
                .endDt(endDt)
                .activate(activate)
                .sender(sender)
                .build();
        return letterbox;
    }

    @Builder
    public LetterboxRequest(String name, String color, LocalDateTime endDt, Boolean activate, Boolean sender) {
        this.name = name;
        this.color = color;
        this.endDt = endDt;
        this.activate = activate;
        this.sender = sender;
    }
}
