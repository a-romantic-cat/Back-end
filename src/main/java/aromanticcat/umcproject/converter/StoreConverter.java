package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.entity.Stamp;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public class StoreConverter {


    public static StoreResponseDTO.LetterPaperResultDTO toLetterPaperResultDTO(LetterPaper letterPaper, List<AcquiredItem> acquiredItemList){
        boolean isAcquired = isItemAcquired(letterPaper, acquiredItemList);
        Integer price = isAcquired ? null : letterPaper.getPrice();

        return StoreResponseDTO.LetterPaperResultDTO.builder()
                .letterPaperId(letterPaper.getId())
                .letterPaperName(letterPaper.getName())
                .letterPaperImageUrl(letterPaper.getImageUrl())
                .price(price)
                .createdAt(letterPaper.getCreatedAt())
                .build();
    }

    public static StoreResponseDTO.LetterPaperResultDTO toAcquiredLetterPaperResultDTO(AcquiredItem letterPaper){
        return StoreResponseDTO.LetterPaperResultDTO.builder()
                .letterPaperId(letterPaper.getLetterPaper().getId())
                .letterPaperName(letterPaper.getLetterPaper().getName())
                .letterPaperImageUrl(letterPaper.getLetterPaper().getImageUrl())
                .price(null)
                .createdAt(letterPaper.getLetterPaper().getCreatedAt())
                .build();
    }


    public static StoreResponseDTO.StampResultDTO toStampResultDTO(Stamp stamp, List<AcquiredItem> acquiredItemList){
        boolean isAcquired = isItemAcquired(stamp, acquiredItemList);
        Integer price = isAcquired ? null : stamp.getPrice();

        return StoreResponseDTO.StampResultDTO.builder()
                .stampId(stamp.getId())
                .stampName(stamp.getName())
                .stampImageUrl(stamp.getImageUrl())
                .price(price)
                .createdAt(stamp.getCreatedAt())
                .build();
    }

    public static StoreResponseDTO.StampResultDTO toAcquiredStampResultDTO(AcquiredItem stamp){
        return StoreResponseDTO.StampResultDTO.builder()
                .stampId(stamp.getStamp().getId())
                .stampName(stamp.getStamp().getName())
                .stampImageUrl(stamp.getStamp().getImageUrl())
                .price(null)
                .createdAt(stamp.getStamp().getCreatedAt())
                .build();
    }

    private static boolean isItemAcquired(LetterPaper letterPaper, List<AcquiredItem> acquiredItemList) {
        Long itemId = letterPaper.getId();
        return acquiredItemList.stream()
                .anyMatch(acquiredItem -> acquiredItem.getLetterPaper() != null && acquiredItem.getLetterPaper().getId().equals(itemId));
    }

    private static boolean isItemAcquired(Stamp stamp, List<AcquiredItem> acquiredItemList) {
        Long itemId = stamp.getId();
        return acquiredItemList.stream()
                .anyMatch(acquiredItem -> acquiredItem.getStamp() != null && acquiredItem.getStamp().getId().equals(itemId));
    }


    public static AcquiredItem toAcquiredItem(Member member, LetterPaper letterPaper) {
        return AcquiredItem.builder()
                .member(member)
                .letterPaper(letterPaper)
                .build();
    }

    public static AcquiredItem toAcquiredItem(Member member, Stamp stamp) {
        return AcquiredItem.builder()
                .member(member)
                .stamp(stamp)
                .build();
    }

}
