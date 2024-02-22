package com.example.carwash.service.favorite;

import com.example.carwash.domain.member.Favorite;
import com.example.carwash.repository.favorite.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    final private FavoriteRepository favoriteRepository;

    public List<Integer> getFavorites(String memberId) {
        return favoriteRepository.getFavorites(memberId);
    }

    public void addHeart(int boardId, String memberId) {
        favoriteRepository.save(
                Favorite.builder()
                        .memberId(memberId)
                        .board_id(boardId)
                        .build()
        );
    }

    @Transactional
    @Modifying
    public void deleteClip(int boardId, String memberId) {
        favoriteRepository.deleteHeart(boardId,memberId);
    }


}
