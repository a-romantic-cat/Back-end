package aromanticcat.umcproject.service.storeService;

import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {

    List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize);

    List<StoreResponseDTO.StampResultDTO> findStampList(Long memberId, int page, int pageSize);

    void purchasedLetterPaper(Long userId, Long letterPaperId);

    void purchasedStamp(Long userId, Long stampId);

}
