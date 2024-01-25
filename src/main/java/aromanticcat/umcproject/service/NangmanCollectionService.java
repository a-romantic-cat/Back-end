package aromanticcat.umcproject.service;

import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;

import java.util.List;

public interface NangmanCollectionService {

    List<NangmanLetterBoxResponseDTO.PreviewBothResultDTO> getCollection(int page, int pageSize);
}
