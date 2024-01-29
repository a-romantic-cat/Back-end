package aromanticcat.umcproject.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LetterboxResponse {
    private Long id;
    private String name;
    private String color;
    private LocalDateTime endDt;
    private Boolean activate;
    private Boolean sender;
}
