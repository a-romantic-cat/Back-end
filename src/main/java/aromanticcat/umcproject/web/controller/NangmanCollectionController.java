package aromanticcat.umcproject.web.controller;


import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.service.nangmanLetterBoxService.NangmanCollectionService;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanCollectionResponseDTO;
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
            description = "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 12)를 주세요."+
                          "낭만 편지의 내용(40자) + 답장 내용(40자)을 반환합니다.")
    public ApiResponse<List<NangmanCollectionResponseDTO.PreviewBothResultDTO>> CollectionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize) {
        try{
            List<NangmanCollectionResponseDTO.PreviewBothResultDTO> collection = nangmanCollectionService.findCollection(page, pageSize);

            return ApiResponse.onSuccess(collection);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }


    }

    @GetMapping("/{nangmanLetterId}")
    @Operation(summary = "낭만모음집 특정 편지 상세 조회 API",
                description = "편지,답장 상세 내용 + 공감 수")
    public ApiResponse<NangmanCollectionResponseDTO.BothResultDTO> CollecitonDetails(@PathVariable Long nangmanLetterId){
        try{
            NangmanCollectionResponseDTO.BothResultDTO details = nangmanCollectionService.findCollectionDetails(nangmanLetterId);

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

    @GetMapping("/my/nangman-letters")
    @Operation(summary = "나의 낭만모음집 내가 쓴 편지 목록 조회 API",
            description = "사용자가 작성한 편지 목록을 조회하는 API입니다." +
                        "편지(40자) + 답장(답장이 있을 경우 40자) + 공감수(공개 편지인 경우)를 반환합니다." +
                        "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 6)를 주세요.")
    public ApiResponse<List<NangmanCollectionResponseDTO.PreviewBothResultDTO>> getMyNangmanLetters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            // 현재 로그인된 사용자의 ID 또는 정보를 얻어온다고 가정
            // SecurityContextHolder에서 현재 사용자의 정보를 가져오는 방법
            Long userId = getCurrentUserId(); // 로그인한 사용자의 아이디를 가져오는 메서드

            // 사용자가 작성한 편지 목록 조회
            List<NangmanCollectionResponseDTO.PreviewBothResultDTO> userLetterList = nangmanCollectionService.getMyLetterList(userId, page, pageSize);

            // 성공 응답 생성
            return ApiResponse.onSuccess(userLetterList);
        } catch (Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }


    @GetMapping("/my/nangman-replies")
    @Operation(summary = "나의 낭만모음집 내가 답장한 편지 목록 조회 API",
            description = "사용자가 답장한 목록을 조회하는 API입니다."+
                        "연결된 낭만 편지의 내용(40자) + 답장 내용(40자) + 공감 수(공개된 편지일 경우)를 반환합니다." +
                        "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 6)를 주세요.")
    public ApiResponse<List<NangmanCollectionResponseDTO.PreviewBothResultDTO>> getMyNangmanReplies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int pageSize) {
        try{
            // 현재 로그인된 사용자의 ID 또는 정보를 얻어온다고 가정
            // SecurityContextHolder에서 현재 사용자의 정보를 가져오는 방법
            Long userId = getCurrentUserId(); // 로그인한 사용자의 아이디를 가져오는 메서드

            // 사용자가 답장한 목록 조회
            List<NangmanCollectionResponseDTO.PreviewBothResultDTO> userReplyList = nangmanCollectionService.getMyReplyList(userId,  page, pageSize);

            // 성공 응답 생성
            return ApiResponse.onSuccess(userReplyList);
        }catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    //스프링 시큐리티 구현되기 전이라 임시 메서드입니다.
    private Long getCurrentUserId(){
        return 1L;
    }

}
