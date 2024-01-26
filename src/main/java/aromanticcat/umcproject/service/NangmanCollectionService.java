package aromanticcat.umcproject.service;

import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;

import java.util.List;

public interface NangmanCollectionService {

    List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> findCollection(int page, int pageSize);

    NangmanLetterBoxResponseDTO.BothResultDTO findCollectionDetails(Long nangmanLetterId);
}
