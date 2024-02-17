package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.MemberService;
import aromanticcat.umcproject.service.myCollectionService.MyCollectionService;
import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;
import io.swagger.annotations.ApiOperation;
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
    private final MemberService memberService;

    @GetMapping("/letter-paper")
    @ApiOperation(value = "내 수집함 편지지 조회 API",
            notes = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12)를 주세요.")
    public ApiResponse<List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO>> getMyLetterPaperList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "false") boolean onlyMyDesign) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> letterPaperList = myCollectionService.findLetterPaperList(
                    userEmail, page, pageSize, onlyMyDesign);

            return ApiResponse.onSuccess(letterPaperList);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/stamp")
    @ApiOperation(value = "내 수집함 우표 조회 API",
            notes = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12), onlyMyDesign(기본값 false)를 주세요.")
    public ApiResponse<List<MyCollectionResponseDTO.AcquiredStampResultDTO>> getMyStampList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "false") boolean onlyMyDesign) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            List<MyCollectionResponseDTO.AcquiredStampResultDTO> stampList = myCollectionService.findStampList(
                    userEmail, page, pageSize, onlyMyDesign);
            return ApiResponse.onSuccess(stampList);


        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

}

