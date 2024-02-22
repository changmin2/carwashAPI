package com.example.carwash.service.community;

import com.example.carwash.domain.member.Block;
import com.example.carwash.domain.member.Favorite;
import com.example.carwash.repository.block.BlockRepository;
import com.example.carwash.repository.favorite.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockService {

    final private BlockRepository blockRepository;

    public List<String> getBlocks(String memberId) {
        return blockRepository.getBlocks(memberId);
    }

    public void addBlock(String memberId, String blockId) {
        blockRepository.save(
                Block.builder()
                        .memberId(memberId)
                        .blockId(blockId)
                        .build()
        );
    }
}
