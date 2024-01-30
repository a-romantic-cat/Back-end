package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.web.dto.nangmanLetterbox.NangmanCollectionResponseDTO;

public class NangmanCollectionConverter {
//
//    public static NangmanLetterBoxResponseDTO.PreviewReplyResultDTO toPreviewReplyResultDTO(NangmanReply nangmanReply) {
//
//        return NangmanLetterBoxResponseDTO.PreviewReplyResultDTO.builder()
//                .nangmanReplyId(nangmanReply.getId())
//                .nangmanLetterId(nangmanReply.getNangmanLetter().getId())
//                .preview(getPreviewText(nangmanReply.getContent()))
//                .totalEmojiCount(calculateTotalEmojiCount(nangmanReply.getNangmanLetter()))
//                .build();
//
//    }


    private static String getPreviewText(String content){
        // 답장 내용을 40자로 제한
        return content.length() <= 40 ? content : content.substring(0, 40) + "...";

    }


    public static NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO toPreviewBothResultDTO(NangmanLetter nangmanLetter){
        NangmanReply nangmanReply = nangmanLetter.getNangmanReply();

        if(nangmanReply == null) {
            //reply가 null인 경우에는 편지 미리보기만
            return NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO.builder()
                    .nangmanLetterId(nangmanLetter.getId())
                    .previewLetter(getPreviewText(nangmanLetter.getContent()))
                    .createAt(nangmanLetter.getCreatedAt())
                    .build();
        }
        if(!nangmanLetter.getIsPublic()) {
            //비공개인 경우 편지+답장 프리뷰
            return NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO.builder()
                    .nangmanLetterId(nangmanLetter.getId())
                    .nangmanReplyId(nangmanReply.getId())
                    .previewLetter(getPreviewText(nangmanLetter.getContent()))
                    .previewReply(getPreviewText(nangmanReply.getContent()))
                    .createAt(nangmanLetter.getCreatedAt())
                    .build();
        }

        //공개인 경우 편지+답장+공감수
        return NangmanCollectionResponseDTO.PreviewLetterAndReplyResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .nangmanReplyId(nangmanReply.getId())
                .previewLetter(getPreviewText(nangmanLetter.getContent()))
                .previewReply(getPreviewText(nangmanReply.getContent()))
                .totalEmojiCount(calculateTotalEmojiCount(nangmanLetter))
                .createAt(nangmanReply.getCreatedAt())
                .build();
    }

    private static Integer calculateTotalEmojiCount(NangmanLetter nangmanLetter){
        // isPublic이 false이거나 hasResponse가 false이면 이모지 수를 계산하지 않음
        if(!nangmanLetter.getIsPublic() || !nangmanLetter.getHasResponse()) {
            return null;
        }

        Integer thumbsUpCnt = nangmanLetter.getThumbsUpCnt();
        Integer heartCnt = nangmanLetter.getHeartCnt();
        Integer cryingCnt = nangmanLetter.getCryingCnt();
        Integer cloverCnt = nangmanLetter.getCloverCnt();
        Integer clapCnt = nangmanLetter.getClapCnt();
        Integer starCnt = nangmanLetter.getStarCnt();

        // 각 이모지 수가 null인 경우 0으로 간주하여 합산
        thumbsUpCnt = thumbsUpCnt != null ? thumbsUpCnt : 0;
        heartCnt = heartCnt != null ? heartCnt : 0;
        cryingCnt = cryingCnt != null ? cryingCnt : 0;
        cloverCnt = cloverCnt != null ? cloverCnt : 0;
        clapCnt = clapCnt != null ? clapCnt : 0;
        starCnt = starCnt != null ? starCnt : 0;

        return thumbsUpCnt + heartCnt + cryingCnt + cloverCnt + clapCnt + starCnt;

    }

    //편지 상세 + 답장 상세
    public static NangmanCollectionResponseDTO.LetterAndReplyResultDTO toBothResultDTO(NangmanLetter nangmanLetter, NangmanReply nangmanReply){
        return NangmanCollectionResponseDTO.LetterAndReplyResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .letterContent(nangmanLetter.getContent())
                .senderNickname(nangmanLetter.getSenderNickname())
                .nangmanReplyId(nangmanReply.getId())
                .replyContent(nangmanReply.getContent())
                .replySenderNickname(nangmanReply.getReplySenderNickname())
                .build();
    }
}

