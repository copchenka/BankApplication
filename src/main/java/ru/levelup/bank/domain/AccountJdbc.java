package ru.levelup.bank.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AccountJdbc {
    private int id;
    private String accountNumber;
    private Date openDatetime;
    private String status;
    private String type;
    private Long bankDocumentId;
}
