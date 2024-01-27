package aromanticcat.umcproject.service.nangmanLetterBoxService;

import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;

import java.util.List;
import java.util.Optional;

public interface NangmanLetterBoxService {

    NangmanLetter sendLetter(NangmanLetterBoxRequestDTO.WriteLetterDTO requestDTO);

    List<NangmanLetter> getLetterList(int page, int pageSize);

    NangmanLetter getLetter(Long nangmanLetterid);

    NangmanReply sendReply(NangmanLetterBoxRequestDTO.WriteReplyDTO requestDTO, Long id);

    List<NangmanLetter> getMyLetterList(Long userId, int page, int pageSize);

    Optional<NangmanReply> getReplyForLetter(Long userId, Long nangmanLetterId);

    List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> getMyReplyList(Long userId, int page, int pageSize);

}
