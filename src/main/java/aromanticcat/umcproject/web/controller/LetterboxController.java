package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.letterbox.LetterboxService;
import aromanticcat.umcproject.web.dto.Letterbox.LetterboxRequest;
import aromanticcat.umcproject.web.dto.Letterbox.LetterboxResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letterboxes")
public class LetterboxController {
    private final LetterboxService letterboxService;

    @PostMapping("/")
    @ApiOperation(value = "우편함 생성")
    public ApiResponse<String> createLetterbox(@RequestBody LetterboxRequest letterboxRequest) {
        return ApiResponse.onSuccess(letterboxService.createLetterbox(letterboxRequest));
    }

    @GetMapping("/{letterboxId}")
    @ApiOperation(value = "우편함 조회")
    public ApiResponse<LetterboxResponse> getLetterboxById(@PathVariable Long letterboxId) {
        LetterboxResponse response = letterboxService.getLetterboxById(letterboxId);
        try {
            return ApiResponse.onSuccess(response);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PatchMapping("/{letterboxId}")
    @ApiOperation(value = "우편함 수정")
    public ApiResponse<LetterboxResponse> updateLetterbox( @PathVariable Long letterboxId, @RequestBody LetterboxRequest request) {
        LetterboxResponse updatedLbResponse = letterboxService.updateLetterbox(letterboxId, request);
        try {
            return ApiResponse.onSuccess(updatedLbResponse);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }
}