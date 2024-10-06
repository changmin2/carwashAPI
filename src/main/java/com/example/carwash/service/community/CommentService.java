package com.example.carwash.service.community;

import com.example.carwash.component.FirebaseUtil;
import com.example.carwash.domain.comment.Comment;
import com.example.carwash.domain.comment.ReComment;
import com.example.carwash.domain.community.Community;
import com.example.carwash.domain.dto.MetaDto;
import com.example.carwash.domain.dto.community.CommentDto;
import com.example.carwash.domain.dto.community.CommentRequestDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.repository.community.CommentRepository;
import com.example.carwash.repository.community.CommunityRepository;
import com.example.carwash.repository.community.ReCommentRepository;
import com.example.carwash.repository.member.MemberRepository;
import com.example.carwash.service.fireabase.FirebaseCloudMessageService;
import com.example.carwash.service.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final ReCommentRepository reCommentRepository;
    private final FirebaseUtil firebaseUtil;

    private final MemberRepository memberRepository;

    @Transactional
    public Comment createComment(String recipe_id, CommentDto commentDto) throws IOException {
        Comment comment = new Comment();
        comment.setCreator(commentDto.getCreator());
        comment.setContent(commentDto.getContent());
        comment.setCreateDate(commentDto.getCreateDate());
        comment.setCommentList_id(Integer.parseInt(recipe_id));
        Community community = communityRepository.findById(Integer.parseInt(recipe_id)).get();
        community.setCommentCnt(community.getCommentCnt()+1);
        List<Comment> commentList = community.getCommentList();
        commentList.add(comment);

        Member member = memberRepository.duplicateNickName(community.getCreator()).get();

        firebaseUtil.sendToMessage("세차노트","새로운 댓글이 달렸어요",member.getFirebaseToken());

        return commentList.get(commentList.size()-1);

    }

    public Map<String,Object> getComments(String recipe_id, CommentRequestDto commentRequestDto){
        int count;
        boolean hasMore = true;

        Optional<List<Comment>> comments = commentRepository.paginate(Integer.parseInt(recipe_id), commentRequestDto.getAfter(), commentRequestDto.getCount());
        List<Comment> commentList = comments.get();
        if(commentList.size()==0){
            return null;
        }

        count = commentList.size();
        if(commentList.get(count-1).getComment_id().equals(Long.parseLong(String.valueOf(commentRepository.getFinalId(Integer.parseInt(recipe_id)))))){
            hasMore = false;
        }

        MetaDto metaDto =new MetaDto();
        metaDto.setCount(count);
        metaDto.setHasMore(hasMore);
        Map<String,Object> map = new HashMap<>();
        map.put("meta",metaDto);
        map.put("data",commentList);

        return map;
    }

    public void deleteComment(String comment_id){
        Comment co = commentRepository.findById(Long.parseLong(comment_id)).get();
        int commentCnt = co.getCommentList().size()+1;
        Community comu = communityRepository.findById(co.getCommentList_id()).get();
        comu.setCommentCnt(comu.getCommentCnt()-commentCnt);
        commentRepository.deleteById(Long.parseLong(comment_id));
    }

    @Transactional
    public ReComment createReComment(String commentId, CommentDto commentDto) {
        ReComment reComment = new ReComment();
        reComment.setCreator(commentDto.getCreator());
        reComment.setContent(commentDto.getContent());
        reComment.setCreateDate(commentDto.getCreateDate());
        reComment.setTarget(commentDto.getTarget());
        Comment comment = commentRepository.findById(Long.parseLong(commentId)).get();
        Community community = communityRepository.findById(comment.getCommentList_id()).get();
        community.setCommentCnt(community.getCommentCnt()+1);
        List<ReComment> commentList = comment.getCommentList();
        commentList.add(reComment);

        return commentList.get(commentList.size()-1);
    }

    public void deleteRecomment(String recomment_id) {
        int comment_id = commentRepository.getCommentId(Integer.parseInt(recomment_id));
        Comment co = commentRepository.findById((long) comment_id).get();
        Community comu = communityRepository.findById(co.getCommentList_id()).get();
        comu.setCommentCnt(comu.getCommentCnt()-1);
        reCommentRepository.deleteById(Long.parseLong(recomment_id));
    }
}
