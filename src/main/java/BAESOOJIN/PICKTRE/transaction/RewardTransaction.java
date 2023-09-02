package BAESOOJIN.PICKTRE.transaction;


import BAESOOJIN.PICKTRE.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RewardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String productName;
    private int productCount;
    private int points; // 적립 또는 사용한 리워드 포인트

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDateTime transactionDateTime; // 트랜잭션 일시


    public RewardTransaction(Member member,String productName,int productCount, int points, LocalDateTime transactionDateTime) {
        this.member = member;
        this.productName=productName;
        this.productCount=productCount;
        this.points = points;
        this.transactionDateTime = transactionDateTime;
    }

}