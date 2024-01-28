package aromanticcat.umcproject.service;

import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {

    List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize, boolean purchasedOnly);

    List<StoreResponseDTO.StampResultDTO> findStampList(Long memberId, int page, int pageSize, boolean purchasedOnly);

    void purchasedLetterPaper(Long userId, Long letterPaperId);
}
