package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.entity.SlowLetter;
import aromanticcat.umcproject.service.slowLetter.SlowLetterService;
import aromanticcat.umcproject.web.dto.slowLetter.SlowLetterCalResponse;
import aromanticcat.umcproject.web.dto.slowLetter.SlowLetterGetResponse;
import aromanticcat.umcproject.web.dto.slowLetter.SlowLetterRequest;
import aromanticcat.umcproject.web.dto.slowLetter.SlowLetterResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://dev.nangmancat.shop")
@RestController
@RequiredArgsConstructor
@RequestMapping("/slow_letters")
public class SlowLetterController {
    private final SlowLetterService slowLetterService;

    @PostMapping("/{memberId}")
    @ApiOperation(value = "느린 편지 생성")
    public ApiResponse<SlowLetterResponse> createApplicant(@PathVariable Long memberId, @RequestBody SlowLetterRequest request){
        try {
            SlowLetter slowLetter = slowLetterService.save(memberId, request);
            return ApiResponse.onSuccess(SlowLetterResponse.from(slowLetter));
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/{memberId}/list")
    @ApiOperation(value = "느린 우편함 리스트 조회 - 연말 확인 시")
    public ApiResponse<List<SlowLetterGetResponse>> getSlowLetters(@PathVariable Long memberId) {
        List<SlowLetterGetResponse> letters = slowLetterService.getSlowLetters(memberId);
        try {
            return ApiResponse.onSuccess(letters);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/{letterId}")
    @ApiOperation(value = "느린 우편함 조회")
    public ApiResponse<SlowLetterGetResponse> getSlowLetter(@PathVariable Long letterId) {
        try {
            return ApiResponse.onSuccess(SlowLetterGetResponse.from(slowLetterService.getSlowLetter(letterId)));
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/{memberId}/{month}")
    public ApiResponse<List<SlowLetterCalResponse>> findDataByMonthAndMemberId(@PathVariable Long memberId, @PathVariable int month) {
        List<SlowLetterCalResponse> days = slowLetterService.getWriteSlowLetters(memberId, month);
        try {
            return ApiResponse.onSuccess(days);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }
}
