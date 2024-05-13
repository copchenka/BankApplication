package ru.levelup.bank.domain;




import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Table(name = "accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "open_datetime")
    private Date openDatetime;
    @Column(name = "status")
    private String status;
    @Column(name = "type")
    private String type;

    //создаем связь между таблицами
    //много  accounts к одному customer
    @ManyToOne
    @JoinColumn(
            name = "bd_id",
            referencedColumnName = "bank_document_id"
    )
    private Customer customer;

    public Account(Integer id, String accountNumber, Date openDatetime, String status, String type, Long bankDocumentId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.openDatetime = openDatetime;
        this.status = status;
        this.type = type;
    }

}
