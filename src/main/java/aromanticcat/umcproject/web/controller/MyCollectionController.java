package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.myCollectionService.MyCollectionService;
import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my-collection")
@RequiredArgsConstructor
public class MyCollectionController {

    private final MyCollectionService myCollectionService;

    @GetMapping("/letter-paper")
    @Operation(summary = "내 수집함 편지지 조회 API",
            description = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12)를 주세요.")
    public ApiResponse<List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO>> getMyLetterPaperList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "false")boolean onlyMyDesign) {
        try {
            // 로그인한 사용자의 아이디를 가져오는 메서드
            Long userId = getCurrentUserId();

            List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> letterPaperList = myCollectionService.findLetterPaperList(userId, page, pageSize, onlyMyDesign);

            return ApiResponse.onSuccess(letterPaperList);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/stamp")
    @Operation(summary = "내 수집함 우표 조회 API",
            description = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12), onlyMyDesign(기본값 false)를 주세요.")
    public ApiResponse<List<MyCollectionResponseDTO.AcquiredStampResultDTO>> getMyStampList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "false")boolean onlyMyDesign) {
        try {
            // 로그인한 사용자의 아이디를 가져오는 메서드
            Long userId = getCurrentUserId();

            List<MyCollectionResponseDTO.AcquiredStampResultDTO> stampList = myCollectionService.findStampList(userId, page, pageSize, onlyMyDesign);
            return ApiResponse.onSuccess(stampList);


        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}

