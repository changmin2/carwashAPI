package com.example.carwash.service.community;

import com.example.carwash.domain.community.Community;
import com.example.carwash.domain.dto.MetaDto;
import com.example.carwash.domain.dto.community.CommunityRequestDto;
import com.example.carwash.repository.community.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    @Transactional
    public void register(Map<String,String> json) throws ParseException {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String date = formatter.format(today);
        Date converDate = formatter.parse(date);
        String imgUrl;
        if(json.get("imgUrl").equals(("[]"))){
            imgUrl = "";
        }else{
            imgUrl = json.get("imgUrl");
        }
        communityRepository.save(
                Community.builder()
                        .creator(json.get("creator"))
                        .content(json.get("content"))
                        .createDate(converDate)
                        .category(json.get("category"))
                        .title(json.get("title"))
                        .hits(0)
                        .favorite(0)
                        .commentCnt(0)
                        .hastag(json.get("hastag"))
                        .imgUrls(imgUrl)
                        .build()
        );
    }

    public Map<String, Object> paginate(CommunityRequestDto requestDto) {
        int count;
        boolean hasMore = true;
        Optional<List<Community>> boardList = communityRepository.paginate(requestDto.getAfter(),requestDto.getCategory());
        List<Community> lists = boardList.get();
        if(lists.size() == 0){
            return null;
        }
        count = lists.size();
        System.out.println(boardList.get().get(count-1).getId());
        System.out.println(communityRepository.getFinalId(requestDto.getCategory()));
        System.out.println(boardList.get().get(count-1).getId().equals(communityRepository.getFinalId(requestDto.getCategory())));
        System.out.println("gheefe");
        if(boardList.get().get(count-1).getId().equals(communityRepository.getFinalId(requestDto.getCategory()))){
            hasMore = false;
        }

        MetaDto metaDto = new MetaDto();
        metaDto.setCount(count);
        metaDto.setHasMore(hasMore);
        Map<String,Object> map = new HashMap<>();
        map.put("meta",metaDto);
        map.put("data",boardList);
        return map;
    }

    public List<Community> recentCommunity() {
        return communityRepository.getRecentCommunity();
    }

    public List<Community> recentFreeCommunity() {
        return communityRepository.getFreeCommunity();
    }

    @Transactional
    public void clickFavorite(int id) {
        Community re = communityRepository.findById(id).get();
        re.setFavorite(re.getFavorite()+1);
    }

    @Transactional
    public void downFavorite(int id) {
        Community re = communityRepository.findById(id).get();
        re.setFavorite(re.getFavorite()-1);
    }
}
