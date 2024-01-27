package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.service.nangmanLetterBoxService.NangmanLetterBoxService;
import aromanticcat.umcproject.service.RandomNicknameService;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nangman-letterbox")
@RequiredArgsConstructor
public class NangmanLetterBoxController {

    private final NangmanLetterBoxService nangmanLetterBoxService;
    private final RandomNicknameService randomNicknameService;

    @GetMapping("/send/random-nickname")
    @Operation(summary = "낭만우편함 편지 작성 - 랜덤 닉네임 생성 API", description = "랜덤 생성된 닉네임을 조회(편지 작성하기 화면)하는 API입니다.")
    public ApiResponse<String> getRandomNickname(){
        try {
            //랜덤 닉네임 생성
            String randomNickname = randomNicknameService.generateRandomNickname();

            // 성공 응답 생성
            return ApiResponse.onSuccess(randomNickname);
        } catch (Exception e){
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
            }
        }


    @PostMapping("/send")
    @Operation(summary = "낭만우편함 편지 작성 - 편지 발송 API", description = "편지의 내용과 공개 여부 데이터를 넘기는 API 입니다. " )
    public ApiResponse<NangmanLetterBoxResponseDTO.SendLetterResultDTO> sendLetter(@RequestBody NangmanLetterBoxRequestDTO.WriteLetterDTO request){
        try{
            //편지 작성 및 발송
            NangmanLetter nangmanLetter = nangmanLetterBoxService.sendLetter(request);

            //성공 응답 생성
            return ApiResponse.onSuccess(NangmanLetterBoxConverter.toWriteLetterResultDTO(nangmanLetter));

        }catch (Exception e){
            //에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping( "/letter-list")
    @Operation(
            summary = "낭만우편함 답장하기 - 편지 목록 조회 API",
            description = "고민 편지 목록을 조회하는 API입니다. " +
                    "각 편지 내용은 40자까지 제공합니다. " +
                    "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 9)를 주세요.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO>> getLetterList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int pageSize){
        try{
            //편지 페이지의 편지 목록 조회
            List<NangmanLetter> letterList = nangmanLetterBoxService.getLetterList(page, pageSize);

            //편지 내용의 두 줄만 포함하도록 변환
            List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO> letterSummaryDTOList = letterList.stream()
                    .map(NangmanLetterBoxConverter::toPreviewLetterResultDTO)
                    .collect(Collectors.toList());

            //성공 응답 생성
            return ApiResponse.onSuccess(letterSummaryDTOList);
        }catch (Exception e){
            //에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }

    @GetMapping( "/letter-list/info/{nangmanLetterId}")
    @Operation(summary  = "낭만우편함 답장하기 - 선택한 편지 상세 조회, 랜덤 닉네임 제공 API",
            description = "선택된 편지의 상세 내용과 작성자의 랜덤 닉네임을 보여주고, 답장하는 사용자의 랜덤 닉네임을 생성해서 보여주는 API입니다. ")
    public ApiResponse<NangmanLetterBoxResponseDTO.SelectedLetterResultDTO> getNangmanLetterInfo(@PathVariable Long nangmanLetterId){
       try{
           // 특정 편지에 대한 정보 조회
           NangmanLetter seledtedLetter = nangmanLetterBoxService.getLetter(nangmanLetterId);

           // 랜덤 닉네임 생성
           String randomNickname = randomNicknameService.generateRandomNickname();

           //응답 생성
           NangmanLetterBoxResponseDTO.SelectedLetterResultDTO selectedLetterResultDTO = NangmanLetterBoxConverter.toSelectedLetterResultDTO(seledtedLetter, randomNickname);

           return ApiResponse.onSuccess(selectedLetterResultDTO);
       }catch (Exception e){

           // 에러 발생 시 실패 응답 반환
           return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
       }
    }

    @PostMapping("/letter-list/{nangmanLetterId}")
    @Operation(summary  = "낭만우편함 답장하기 - 답장 발송 API")
    public ApiResponse<NangmanLetterBoxResponseDTO.WriteReplyResultDTO> sendReply(@PathVariable Long nangmanLetterId, @RequestBody NangmanLetterBoxRequestDTO.WriteReplyDTO request){
        try{
            //답장 작성 및 발송
            NangmanReply nangmanReply = nangmanLetterBoxService.sendReply(request, nangmanLetterId);

            //성공 응답 생성
            return ApiResponse.onSuccess(NangmanLetterBoxConverter.toWriteReplyResultDTO(nangmanReply));


        } catch(Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }

    @GetMapping("/my/nangman-letters")
    @Operation(summary = "낭만우편함 나의 편지 목록 조회 API",
            description = "사용자가 작성한 낭만 편지 목록을 조회하는 API입니다." +
                        "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 6)를 주세요.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO>> getMyNangmanLetters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            // 현재 로그인된 사용자의 ID 또는 정보를 얻어온다고 가정
            // SecurityContextHolder에서 현재 사용자의 정보를 가져오는 방법
            Long userId = getCurrentUserId(); // 로그인한 사용자의 아이디를 가져오는 메서드

            // 사용자가 작성한 편지 목록 조회
            List<NangmanLetter> userLetterList = nangmanLetterBoxService.getMyLetterList(userId, page, pageSize);

            // 편지 내용의 두 줄만 포함하도록 변환
            List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO> previewLetterList = userLetterList.stream()
                    .map(NangmanLetterBoxConverter::toPreviewLetterResultDTO)
                    .collect(Collectors.toList());

            // 성공 응답 생성
            return ApiResponse.onSuccess(previewLetterList);
        } catch (Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/my/nangman-letters/{nangmanLetterId}/preview-reply")
    @Operation(summary = "낭만우편함 내가 쓴 편지에 받은 답장 조회 API",
            description = "특정 편지에 대한 답장 내용을 40자까지 제공합니다.")
    public ApiResponse<NangmanLetterBoxResponseDTO.PreviewReplyResultDTO> getPreviewReplyForLetter(@PathVariable Long nangmanLetterId) {
        try {
            // 현재 로그인된 사용자의 ID 또는 정보를 얻어온다고 가정
            // SecurityContextHolder에서 현재 사용자의 정보를 가져오는 방법
            Long userId = getCurrentUserId(); // 로그인한 사용자의 아이디를 가져오는 메서드

            // 특정 편지에 대한 답장 조회
            Optional<NangmanReply> replyOptional = nangmanLetterBoxService.getReplyForLetter(userId, nangmanLetterId);

            // 답장이 존재하는 경우에만 프리뷰로 변환
            NangmanLetterBoxResponseDTO.PreviewReplyResultDTO previewReplyResultDTO = replyOptional.map(NangmanLetterBoxConverter::toPreviewReplyResultDTO)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아직 답장이 없습니다."));

            // 성공 응답 생성
            return ApiResponse.onSuccess(previewReplyResultDTO);

        } catch (Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/my/nangman-replies")
    @Operation(summary = "낭만우편함 내가 답장한 목록 조회 API",
            description = "사용자가 답장한 목록을 조회하는 API입니다."+
                        "답장 내용(40자) + 연결된 낭만 편지의 내용(40자)를 제공합니다." +
                        "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 6)를 주세요.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO>> getMyNangmanReplies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int pageSize) {
        try{
            // 현재 로그인된 사용자의 ID 또는 정보를 얻어온다고 가정
            // SecurityContextHolder에서 현재 사용자의 정보를 가져오는 방법
            Long userId = getCurrentUserId(); // 로그인한 사용자의 아이디를 가져오는 메서드

            // 사용자가 답장한 목록 조회
            List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> userReplyList = nangmanLetterBoxService.getMyReplyList(userId,  page, pageSize);

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
