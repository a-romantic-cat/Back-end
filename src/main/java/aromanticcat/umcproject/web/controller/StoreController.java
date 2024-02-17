package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.MemberService;
import aromanticcat.umcproject.service.storeService.StoreService;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final MemberService memberService;

    @GetMapping("/letter-papers")
    @ApiOperation(value = "상점 편지지 목록 조회 API",
            notes = "사용자가 이미 구매한 편지지는 가격을 null로 반환합니다. " +
                    "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 16)를 주세요.")
    public ApiResponse<List<StoreResponseDTO.LetterPaperResultDTO>> getAllLetterPaperList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int pageSize) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            List<StoreResponseDTO.LetterPaperResultDTO> letterPaperList = storeService.findLetterPaperList(userEmail,
                    page, pageSize);

            return ApiResponse.onSuccess(letterPaperList);
        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/stamps")
    @ApiOperation(value = "상점 우표 목록 조회 API",
            notes = "사용자가 이미 구매한 우표는 가격을 null로 반환합니다. " +
                    "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 15)를 주세요.")
    public ApiResponse<List<StoreResponseDTO.StampResultDTO>> getAllStampList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int pageSize) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            List<StoreResponseDTO.StampResultDTO> stampList = storeService.findStampList(userEmail, page, pageSize);

            return ApiResponse.onSuccess(stampList);
        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/letter-papers/{letterPaperId}")
    @ApiOperation(value = "편지지 구매 API")
    public ApiResponse<String> purchasedLetterPaper(@PathVariable Long letterPaperId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            storeService.purchasedLetterPaper(userEmail, letterPaperId);

            return ApiResponse.onSuccess("편지지를 성공적으로 구매하였습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }

    @PostMapping("/stamps/{stampId}")
    @ApiOperation(value = "우표 구매 API")
    public ApiResponse<String> purchasedStamp(@PathVariable Long stampId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            storeService.purchasedStamp(userEmail, stampId);

            return ApiResponse.onSuccess("우표를 성공적으로 구매하였습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }
}
