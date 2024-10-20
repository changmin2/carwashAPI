package com.example.carwash.controller.community;

import com.example.carwash.domain.comment.Comment;
import com.example.carwash.domain.comment.ReComment;
import com.example.carwash.domain.dto.community.CommentDto;
import com.example.carwash.domain.dto.community.CommentRequestDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.service.community.CommentService;
import com.example.carwash.service.fireabase.FirebaseCloudMessageService;
import com.example.carwash.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private  final FirebaseCloudMessageService firebaseCloudMessageService;

    //댓글 생성
    @PostMapping("/{id}")
    public Comment createComment(@PathVariable("id")String board_id, @RequestBody CommentDto commentDto) throws ParseException, IOException {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 포맷팅 적용
        Date formatedNow = formatter.parse(formatter.format(now));

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatedNow);
        cal.add(Calendar.HOUR,9);

        commentDto.setCreateDate(cal.getTime());

        Comment comment = commentService.createComment(board_id,commentDto);

        //해당 게시글 작성자에게 댓글 작성 푸쉬 알림
        String creatorFirebaseToken = memberService.getRcvAlramYMember(commentDto.getCreator());
        if(!creatorFirebaseToken.isEmpty()){
            firebaseCloudMessageService.sendMessageTo(creatorFirebaseToken,"세차노트","게시물에 댓글이 달렸어요.","community/"+board_id.toString());
        }

        return comment;
    }

    //댓글 조회
    @GetMapping("/{id}")
    public Map<String,Object> getComments(@PathVariable("id")String recipe_id, @ModelAttribute CommentRequestDto commentRequestDto){
        return commentService.getComments(recipe_id,commentRequestDto);
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id")String comment_id){
        commentService.deleteComment(comment_id);
    }

    //대댓글 생성
    @PostMapping("/recomment/{id}")
    public ReComment createReComment(@PathVariable("id")String comment_id, @RequestBody CommentDto commentDto) throws ParseException {
        System.out.println(commentDto.toString() + "hihi");
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 포맷팅 적용
        Date formatedNow = formatter.parse(formatter.format(now));

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatedNow);
        cal.add(Calendar.HOUR,9);

        commentDto.setCreateDate(cal.getTime());

        System.out.println(comment_id + "코멘토아이디");
        return commentService.createReComment(comment_id,commentDto);
    }

    @DeleteMapping("/recomment/{id}")
    public void deleteReComment(@PathVariable("id")String recomment_id){
        commentService.deleteRecomment(recomment_id);
    }

}
