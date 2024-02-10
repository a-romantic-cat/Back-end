package aromanticcat.umcproject.service.storeService;

import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {

    List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(String email, int page, int pageSize);

    List<StoreResponseDTO.StampResultDTO> findStampList(String email, int page, int pageSize);

    void purchasedLetterPaper(String email, Long letterPaperId);

    void purchasedStamp(String email, Long stampId);

}
