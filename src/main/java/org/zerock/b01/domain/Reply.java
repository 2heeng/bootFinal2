package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "board") //단방향 설정
public class Reply extends BaseEntity{

    //주키를 꼭 선언해주어야 @Entity를 해도 오류가 나지 않는다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY) //글하나당 reply여러개(다대일) + LAZY:하나불러오고 하나 불러오는 방식
    private Board board;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }




}
