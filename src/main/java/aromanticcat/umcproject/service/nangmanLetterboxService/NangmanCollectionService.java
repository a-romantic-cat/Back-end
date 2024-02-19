package aromanticcat.umcproject.service.nangmanLetterboxService;

import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanCollectionResponseDTO;
import org.springframework.data.domain.Page;

public interface NangmanCollectionService {

    Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> findCollection(int page, int pageSize, String sort);

    NangmanCollectionResponseDTO.LetterAndReplyResultDTO findCollectionDetails(Long nangmanLetterId);

    void likeCollection(Long nangmanLetterId,String emojiType);

    Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyLetterPage(String email, int page, int pageSize);

    Page<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyReplyPage(String email, int page, int pageSize);

}
