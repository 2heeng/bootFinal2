package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements  ReplyService{


    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;


    @Override
    public Long register(ReplyDTO replyDTO) {
//        modelMapper.getConfiguration()
//                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
//                .setFieldMatchingEnabled(true);


        Long bno = replyDTO.getBno();
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText(replyDTO.getReplyText())
                .replyer(replyDTO.getReplyer())
                .build();


        //Reply reply = modelMapper.map(replyDTO,Reply.class);

        log.info("여기여기여기: "+replyDTO);
        log.info(reply);


        Long rno = replyRepository.save(reply).getRno();

        log.info("replyServiceimpl reply.getBoard() : =================================================="+reply.getBoard());
//        log.info("replyServiceimpl bno : =================================================="+reply.getBoard().getBno());
        log.info("replyServiceimpl rno : =================================================="+rno);

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> replyOptional = replyRepository.findById(rno);

        Reply reply = replyOptional.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());

        Reply reply = replyOptional.orElseThrow();

        reply.changeText(replyDTO.getReplyText());

        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        log.info("replyServiceImpl에서 getListOfBoard의 pageRequestDTO: "+pageRequestDTO);
        log.info("replyServiceImpl에서 getListOfBoard의 pageRequestDTO.getPage(): "+pageRequestDTO.getPage());

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());
        log.info("replyServiceImpl에서 getListOfBoard의 bno: "+bno);
        log.info("replyServiceImpl에서 getListOfBoard의 pageable"+pageable);
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        log.info("ReplyServiceImpl안에 getListOfBoard의 result "+result.getContent());

        List<ReplyDTO> dtoList =
                result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                        .collect(Collectors.toList());

        log.info("getListOfBoard안에서의 dtoList: "+dtoList);


        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
