package aromanticcat.umcproject.web.controller;


import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.nangmanLetterBoxService.NangmanCollectionService;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nangman-collection")
@RequiredArgsConstructor
public class NangmanCollectionController {

    private final NangmanCollectionService nangmanCollectionService;

    @GetMapping("/")
    @Operation(summary = "낭만모음집 목록 조회 API",
            description = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12)를 주세요.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO>> CollectionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize) {
        try{
            List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> collection = nangmanCollectionService.findCollection(page, pageSize);

            return ApiResponse.onSuccess(collection);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }


    }

    @GetMapping("/{nangmanLetterId}")
    @Operation(summary = "낭만모음집 상세 조회 API")
    public ApiResponse<NangmanLetterBoxResponseDTO.BothResultDTO> CollecitonDetails(@PathVariable Long nangmanLetterId){
        try{
            NangmanLetterBoxResponseDTO.BothResultDTO details = nangmanCollectionService.findCollectionDetails(nangmanLetterId);

            return ApiResponse.onSuccess(details);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/{nangmanLetterId}/like")
    @Operation(summary = "공감 이모지 카운트 증가 API",
                description = "'thumbsUp', 'heart', 'cry', 'clover', 'clap', 'star' 중 하나의 값을 emojiType으로 전달하여 해당 이모지 카운트를 증가시킵니다.")
    public ApiResponse<String> CollectionLike(@PathVariable Long nangmanLetterId, @RequestParam String emojiType){
        try{
            nangmanCollectionService.likeCollection(nangmanLetterId, emojiType);

            return ApiResponse.onSuccess("공감이 성공적으로 반영되었습니다.");

        }catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }
}
