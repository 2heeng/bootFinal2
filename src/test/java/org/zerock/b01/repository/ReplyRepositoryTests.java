package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import javax.transaction.Transactional;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Test
//    public void testInsert() {
//
//        //실제 DB에 있는 bno
//        Long bno = 100L;
//
//        Board board = Board.builder().bno(bno).build();
//
//        Reply reply = Reply.builder()
//                .board(board)
//                .replyText("댓글.....")
//                .replyer("replyer1")
//                .build();
//
//        replyRepository.save(reply);
//
//    }
//
//    @Test
//    @Transactional
//    public void testBoardReplies() {
//        Long bno = 99L;
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
//
//        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
//
//        log.info(result.getContent()+"=============================");
//
//        result.getContent().forEach( reply -> {
//                    log.info("reply"+reply);
//                });
//
//    }


}
