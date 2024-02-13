package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.letterbox.LetterService;
import aromanticcat.umcproject.web.dto.Letterbox.LetterRequest;
import aromanticcat.umcproject.web.dto.Letterbox.LetterResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://dev.nangmancat.shop")
@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/")
    @ApiOperation(value = "편지 생성")
    public ApiResponse<Long> createLetter(@RequestBody LetterRequest request) {
        return ApiResponse.onSuccess(letterService.createLetter(request));
    }

    @GetMapping("/{letterId}")
    @ApiOperation(value = "편지 조회")
    public ApiResponse<LetterResponse> getLetterById(@PathVariable Long letterId) {
        LetterResponse response = letterService.getLetterById(letterId);
        try {
            return ApiResponse.onSuccess(response);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }
}
