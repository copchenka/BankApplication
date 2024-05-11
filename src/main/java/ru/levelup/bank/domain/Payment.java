package ru.levelup.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@Getter
@ToString
public class Payment {
    private int id;
    private Date datetime;
    private Long amount;
    private Integer accountFrom;
    private Integer accountTo;
    private PaymentStatus status;
}
