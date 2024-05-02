package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.Member;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {



    private final ReplyService replyService;

//    @PostMapping("/test")
//    public void test(){
//        log.info("test");
//    }
//
//    @PostMapping("/test2")
//    public void test2(int num){
//        log.info("num");
//    }
//
//    @PostMapping("/test3")
//    public void test3(Member member){
//        log.info(member);
//    }
//
//    @PostMapping("/test4")
//    public Map<String,Member> test4(@RequestBody  Member member){
//        Map<String,Member> map = new HashMap<>();
//        map.put("member1: ",member);
//        log.info(member);
//
//        return map;
//    }
//
//    @PostMapping(value = "/test5",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String,Member> test5(@RequestBody  Member member){
//        Map<String,Member> map = new HashMap<>();
//        map.put("member1: ",member);
//        log.info(member);
//
//        return map;
//    }


//    @ApiOperation(value = "Replies POST", notes = "Post 방식으로 댓글 등록")
//    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE) //해당 메소드를 받아서 소비(consume)하는 데이터가 어떤 종류인지 명시, JSON 데이터 타입을 처리하는 메소드
//    public ResponseEntity<Map<String,Long>> register(@RequestBody ReplyDTO replyDTO){
//        //@RequestBody는 JSON 문자열을 ReplyDTO로 변환
//
//        Map<String,Long> resultMap = new HashMap<>();
//        resultMap=Map.of("rno",111L);
//        //resultMap.put("replyDTO",replyDTO.getBno());
//
//        log.info("replyDTO: "+replyDTO);
//
//        return ResponseEntity.ok(resultMap);
//
//    }



    @ApiOperation(value = "Replies POST", notes = "Post 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE) //해당 메소드를 받아서 소비(consume)하는 데이터가 어떤 종류인지 명시, JSON 데이터 타입을 처리하는 메소드
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult) throws BindException{


        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String,Long> resultMap = new HashMap<>();


        Long rno = replyService.register(replyDTO);

        log.info("========================replyDTO bno: "+replyDTO.getBno());
        log.info("========================replyController rno: "+rno);
        resultMap.put("rno",rno);

        return resultMap;
    }



    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO){

        log.info("replycontroller에서 getList의 pageRequestDTO.getPage(): "+pageRequestDTO.getPage());

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);

        log.info("reply컨트롤러의 getList의 bno: "+bno);
        return responseDTO;
    }


    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO( @PathVariable("rno") Long rno ){

        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }


    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String,Long> remove( @PathVariable("rno") Long rno ){

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }



    @ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public Map<String,Long> remove( @PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO ){

        replyDTO.setRno(rno); //번호를 일치시킴

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }
}
