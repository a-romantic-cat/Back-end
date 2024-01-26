package aromanticcat.umcproject.web.controller;


import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.NangmanCollectionService;
import aromanticcat.umcproject.service.NangmanLetterBoxService;
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
    private final NangmanLetterBoxService nangmanLetterBoxService;

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



}
