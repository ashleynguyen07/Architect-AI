package com.example.architectai.dto;

import com.example.architectai.entity.SubscriptionType;
import com.example.architectai.entity.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTransactionRequestDto {

    private Integer amount;
    private SubscriptionType subscriptionType;
    private TransactionStatus status;
}
