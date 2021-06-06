package com.agh.hr.persistence.service.application;

import com.agh.hr.persistence.model.*;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConverters {

    public Delegation toDelegation(DelegationApplication delegationApplication) {
        return Delegation.builder()
                .user(delegationApplication.getUser())
                .startDate(delegationApplication.getStartDate())
                .endDate(delegationApplication.getEndDate())
                .destination(delegationApplication.getDestination())
                .delegationApplication(delegationApplication)
                .build();
    }

    public Bonus toBonus(BonusApplication bonusApplication) {
        return Bonus.builder()
                .user(bonusApplication.getUser())
                .startDate(bonusApplication.getStartDate())
                .endDate(bonusApplication.getEndDate())
                .amount(bonusApplication.getMoney())
                .bonusApplication(bonusApplication)
                .build();
    }

    public Leave toLeave(LeaveApplication leaveApplication) {
        return Leave.builder()
                .user(leaveApplication.getUser())
                .startDate(leaveApplication.getStartDate())
                .endDate(leaveApplication.getEndDate())
                .paid(leaveApplication.isPaid())
                .leaveApplication(leaveApplication)
                .build();
    }
}
