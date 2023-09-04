package BAESOOJIN.PICKTRE.domain.member;

import BAESOOJIN.PICKTRE.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;
    private String userName;

    private String picture;

    private int totalReward; // 적립한 reward
    private int todayReward; // 오늘 적립함 reward
    private int rewardPoints; // 보유한 reward
    private int trashCount;

    @Enumerated(EnumType.STRING)
    private MemberTier memberTier;

    private String tierPath;

    private LocalDate lastRewardResetDate;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();


    // Constructor
    @Builder
    public Member(String mail,String userName,String picture) {
        this.mail=mail;
        this.userName=userName;
        this.picture=picture;
        this.trashCount=0;
        this.todayReward=0;
        this.rewardPoints = 0;
        this.memberTier=MemberTier.TIER1;
    }

    // Method to add reward points
    public void addRewardPoints(int points) {
        this.rewardPoints += points;
        this.todayReward+=points;
    }

    // Method to subtract reward points
    public void deductRewardPoints(int points) {
        this.rewardPoints -= points;
        this.todayReward-=points;
    }


    public void resetTodayReward() {
        LocalDate currentDate = LocalDate.now();
        if (lastRewardResetDate == null || !lastRewardResetDate.isEqual(currentDate)) {
            todayReward = 0;
            lastRewardResetDate = currentDate;
        }
    }

    // Other methods for managing rewards, orders, etc.
}