package aromanticcat.umcproject.service.nangmanLetterboxService;

import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanCollectionResponseDTO;

import java.util.List;

public interface NangmanCollectionService {

    List<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> findCollection(int page, int pageSize);

    NangmanCollectionResponseDTO.LetterAndReplyResultDTO findCollectionDetails(Long nangmanLetterId);

    void likeCollection(Long nangmanLetterId,String emojiType);

    List<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyLetterList(Long userId, int page, int pageSize);

    List<NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO> getMyReplyList(Long userId, int page, int pageSize);

}