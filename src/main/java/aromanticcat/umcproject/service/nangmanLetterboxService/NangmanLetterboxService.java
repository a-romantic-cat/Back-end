package aromanticcat.umcproject.service.nangmanLetterboxService;

import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterboxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanLetterBoxResponseDTO;

import java.util.List;

public interface NangmanLetterboxService {

    NangmanLetter sendLetter(Long memberId, NangmanLetterboxRequestDTO.SendLetterDTO requestDTO);

    List<NangmanLetter> getLetterList(Long memberId, int page, int pageSize);

    NangmanLetter getLetter(Long nangmanLetterid);

    NangmanLetterBoxResponseDTO.SelectedLetterResultDTO getLetterInfo(Long nangmanLetterId);

    NangmanLetterBoxResponseDTO.SendReplyResultDTO sendReply(Long memberId, NangmanLetterboxRequestDTO.SendReplyDTO requestDTO, Long id);

}
