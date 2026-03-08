package com.omaru.wedding.service;

import com.omaru.wedding.dto.InviteUpdateRequestDto;
import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.exception.InvalidAllergySelectionException;
import com.omaru.wedding.exception.InviteNotFoundException;
import com.omaru.wedding.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private static final String ALLERGY_NONE = "none";

    @Transactional(readOnly = true)
    public InviteEntity getByTokenOrThrow(String token) {
        return inviteRepository.findByInviteToken(token).orElseThrow(InviteNotFoundException::new);
    }

    @Transactional
    public InviteEntity update(String token, InviteUpdateRequestDto request) {
        var entity = getByTokenOrThrow(token);
        entity.setAttendance(request.attendance());
        entity.setName(request.name());
        entity.setCompanionsText(request.companionsText());

        //アレルギー情報を取得
        var allergiesList = request.allergiesList();

        //nullや空チェック
        ensureValidAllergies(allergiesList);

        //カンマ区切りに変換
        var allergiesText = String.join(", ", allergiesList);
        entity.setAllergiesText(allergiesText);


        return inviteRepository.save(entity);
    }

    //nullとアレルギー項目を選択しているのかを確認する
    private void ensureValidAllergies(List<String> allergiesList) {
        //アレルギー情報がない場合に返す内容
        if (allergiesList == null || allergiesList.isEmpty()) {
            throw new InvalidAllergySelectionException("アレルギー情報を選択してください");
        }
//アレルギー情報がほかの選択項目と選択しないが同時に選択できないようにする
        if (allergiesList.contains(ALLERGY_NONE) && allergiesList.size() > 1) {
            throw new InvalidAllergySelectionException("「なし」は他の項目と同時に選択できません");
        }
    }
}
