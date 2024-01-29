package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.letterbox.LetterboxService;
import aromanticcat.umcproject.web.dto.LetterboxRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letterbox")
public class LetterboxController {
    private final LetterboxService letterboxService;

    @PostMapping("/")
    @ApiOperation(value = "우편함 생성")
    public ApiResponse<String> createLetterbox(@RequestBody LetterboxRequest letterboxRequest) {
        return ApiResponse.onSuccess(letterboxService.createLetterbox(letterboxRequest));
    }
}