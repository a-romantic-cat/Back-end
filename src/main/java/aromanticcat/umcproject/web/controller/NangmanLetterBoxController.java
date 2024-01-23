package aromanticcat.umcproject.web.controller;

import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.converter.NangmanLetterBoxConverter;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.service.NangmanLetterBoxService;
import aromanticcat.umcproject.service.RandomNicknameService;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nangman-letterbox")
@RequiredArgsConstructor
public class NangmanLetterBoxController {

    private final NangmanLetterBoxService nangmanLetterBoxService;
    private final RandomNicknameService randomNicknameService;

    @GetMapping("/send/random-nickname")
    @Operation(summary = "낭만우편함 편지 작성 -> 랜덤 닉네임 생성 API", description = "랜덤 생성된 닉네임을 조회(편지 작성하기 화면)하는 API입니다.")
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
    @Operation(summary = "낭만우편함 편지 작성 -> 고민 편지 발송 API", description = "편지의 내용과 공개 여부 데이터를 넘기는 API 입니다. " )
    public ApiResponse<NangmanLetterBoxResponseDTO.WriteLetterResultDTO> sendLetter(@RequestBody NangmanLetterBoxRequestDTO.WriteLetterDTO request){
        try{
            //편지 작성 및 발송
//            String senderNickname = request.getSenderRandomNickname();
//            NangmanLetter nangmanLetter = nangmanLetterBoxService.writeAndSendLetter(request, senderNickname);
            NangmanLetter nangmanLetter = nangmanLetterBoxService.writeAndSendLetter(request);


            //성공 응답 생성
            return ApiResponse.onSuccess(NangmanLetterBoxConverter.toWriteLetterResultDTO(nangmanLetter));

        }catch (Exception e){
            //에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/letter-list")
    @Operation(summary = "낭만우편함 답장하기 -> 편지 목록 조회 API", description = "고민 편지 목록을 조회하는 API입니다. 각 편지는 40자까지 미리보기가 가능합니다.")
    public ApiResponse<List<NangmanLetterBoxResponseDTO.PreviewLetterResultDTO>> getLetterList(){
        try{
            //편지 목록 조회
            List<NangmanLetter> letterList = nangmanLetterBoxService.getLetterList();

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

    @GetMapping("/letter-list/{nangmanLetterId}")
    @Operation(summary  = "낭만우편함 답장하기 -> 답장할 편지 선택+조회 API", description = "선택된 편지의 상세 내용과 작성자의 랜덤 닉네임을 보여주고, 답장하는 사용자의 랜덤 닉네임을 생성해서 보여주는 API입니다. ")
    public ApiResponse<NangmanLetterBoxResponseDTO.SelectedLetterResultDTO> getNangmanLetterInfo(@PathVariable Long nangmanLetterId){
       try{
           // 특정 편지에 대한 정보 조회
           NangmanLetter seledtedLetter = nangmanLetterBoxService.getLetterById(nangmanLetterId);

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
    @Operation(summary  = "낭만우편함 답장하기 -> 답장 발송 API")
    public ApiResponse<NangmanLetterBoxResponseDTO.WriteReplyResultDTO> sendReply(@PathVariable Long nangmanLetterId, @RequestBody NangmanLetterBoxRequestDTO.WriteReplyDTO request){
        try{
            //답장 작성 및 발송
            NangmanReply nangmanReply = nangmanLetterBoxService.writeAndSendReply(request, nangmanLetterId);

            //성공 응답 생성
            return ApiResponse.onSuccess(NangmanLetterBoxConverter.toWriteReplyResultDTO(nangmanReply));


        } catch(Exception e) {
            // 에러 발생 시 실패 응답 반환
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        }
    }
}
