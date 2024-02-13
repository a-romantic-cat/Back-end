package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.service.MemberService;
import aromanticcat.umcproject.service.nangmanLetterboxService.NangmanLetterboxService;
import aromanticcat.umcproject.service.nangmanLetterboxService.RandomNicknameService;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterBoxResponseDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterboxRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://dev.nangmancat.shop")
@RestController
@RequestMapping("/nangman-letterbox")
@RequiredArgsConstructor
public class NangmanLetterboxController {

    private final NangmanLetterboxService nangmanLetterBoxService;
    private final RandomNicknameService randomNicknameService;
    private final MemberService memberService;

    @GetMapping("/random-nickname")
    @Operation(summary = "랜덤 닉네임 생성 API", description = "랜덤 생성된 닉네임을 반환하는 API입니다.")
    public ApiResponse<String> getRandomNickname() {
        try {
            //랜덤 닉네임 생성
            String randomNickname = randomNicknameService.generateRandomNickname();

            // 성공 응답 생성
            return ApiResponse.onSuccess(randomNickname);
        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }


    @PostMapping("/send")
    @Operation(summary = "고민 편지 발송 API"
            , description = "편지의 내용과 공개 여부 데이터를 넘기는 API 입니다. ")
    public ApiResponse<NangmanLetterBoxResponseDTO.SendLetterResultDTO> sendLetter(
            @RequestBody NangmanLetterboxRequestDTO.SendLetterDTO request) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            //편지 작성 및 발송
            NangmanLetter nangmanLetter = nangmanLetterBoxService.sendLetter(userEmail, request);

            //성공 응답 생성
            return ApiResponse.onSuccess(NangmanLetterBoxConverter.toWriteLetterResultDTO(nangmanLetter));

        } catch (Exception e) {
            //에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/letter-list")
    @Operation(
            summary = "답장 기다리는 편지 목록조회 API",
            description = "답장을 기다리는 편지 목록(본인이 쓴 편지 제외)을 조회하는 API입니다. " +
                    "각 편지 내용은 40자까지 제공합니다. " +
                    "페이징을 포함합니다. query String으로 page(기본값 0)와 pageSize(기본값 9)를 주세요.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO>> getLetterList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int pageSize) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();


            //편지 페이지의 편지 목록 조회
            List<NangmanLetter> letterList = nangmanLetterBoxService.getLetterList(userEmail, page, pageSize);

            //편지 내용의 두 줄만 포함하도록 변환
            List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO> letterSummaryDTOList = letterList.stream()
                    .map(NangmanLetterBoxConverter::toPreviewLetterResultDTO)
                    .collect(Collectors.toList());

            //성공 응답 생성
            return ApiResponse.onSuccess(letterSummaryDTOList);
        } catch (Exception e) {
            //에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }

    @GetMapping("/letter-list/{nangmanLetterId}")
    @Operation(summary = "답장할 편지 상세 조회 API",
            description = "답장할 편지의 상세 내용 + 보낸 사람 닉네임을 조회하는 API입니다. ")
    public ApiResponse<NangmanLetterBoxResponseDTO.SelectedLetterResultDTO> getNangmanLetterInfo(
            @PathVariable Long nangmanLetterId) {
        try {
            // 특정 편지에 대한 정보 조회
            NangmanLetterBoxResponseDTO.SelectedLetterResultDTO seledtedLetter = nangmanLetterBoxService.getLetterInfo(
                    nangmanLetterId);

            return ApiResponse.onSuccess(seledtedLetter);
        } catch (Exception e) {

            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/letter-list/{nangmanLetterId}")
    @Operation(summary = "답장 발송 API")
    public ApiResponse<NangmanLetterBoxResponseDTO.SendReplyResultDTO> sendReply(@PathVariable Long nangmanLetterId,
                                                                                 @RequestBody NangmanLetterboxRequestDTO.SendReplyDTO request) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();


            NangmanLetterBoxResponseDTO.SendReplyResultDTO replyResultDTO = nangmanLetterBoxService.sendReply(userEmail,
                    request, nangmanLetterId);

            //성공 응답 생성
            return ApiResponse.onSuccess(replyResultDTO);


        } catch (Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }


}
