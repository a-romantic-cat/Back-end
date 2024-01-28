package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.StoreService;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/letter-papers")
    @Operation(summary = "상점 편지지 목록 조회 API",
            description = "사용자가 이미 구매한 편지지는 가격을 null로 반환합니다." +
                        "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 16)를 주세요." +
                        "구매한 편지지만 보려면 query String으로 purchasedOnly(기본값 false)를 true로 주세요.")
    public ApiResponse<List<StoreResponseDTO.LetterPaperResultDTO>> getAllLetterPaperList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int pageSize,
            @RequestParam(defaultValue = "false") boolean purchasedOnly) {
        try{
            // 로그인한 사용자의 아이디를 가져오는 메서드
            Long userId = getCurrentUserId();

            List<StoreResponseDTO.LetterPaperResultDTO> letterPaperList = storeService.findLetterPaperList(userId, page, pageSize, purchasedOnly);

            return ApiResponse.onSuccess(letterPaperList);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/stamps")
    @Operation(summary = "상점 우표 목록 조회 API",
            description = "사용자가 이미 구매한 우표는 가격을 null로 반환합니다." +
                    "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 15)를 주세요." +
                    "구매한 우표만 보려면 query String으로 purchasedOnly(기본값 false)를 true로 주세요.")
    public ApiResponse<List<StoreResponseDTO.StampResultDTO>> getAllStampList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int pageSize,
            @RequestParam(defaultValue = "false") boolean purchasedOnly) {
        try{
            // 로그인한 사용자의 아이디를 가져오는 메서드
            Long userId = getCurrentUserId();

            List<StoreResponseDTO.StampResultDTO> stampList = storeService.findStampList(userId, page, pageSize, purchasedOnly);

            return ApiResponse.onSuccess(stampList);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/letter-papers/{letterPaperId}")
    @Operation(summary = "편지지 구매 API")
    public ApiResponse<String> purchasedLetterPaper(@PathVariable Long letterPaperId) {
        try{
            // 로그인한 사용자의 아이디를 가져오는 메서드
            Long userId = getCurrentUserId();

            storeService.purchasedLetterPaper(userId, letterPaperId);

            return ApiResponse.onSuccess("편지지를 성공적으로 구매하였습니다.");

        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }

    //스프링 시큐리티 구현되기 전이라 임시 메서드입니다.
    private Long getCurrentUserId(){
        return 1L;
    }
}
