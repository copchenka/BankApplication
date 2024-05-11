package ru.levelup.bank.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;


@Getter
@ToString(
        exclude = {
                "accounts",
                "organizations"}
)
@NoArgsConstructor//для hibernate
@Entity//для hibernate
@Table(name = "customers") //если не совпадают имя табл и класса
public class Customer {
    @Id //primarykey должен быть всегда
    @GeneratedValue(strategy = GenerationType.IDENTITY)//поле сгенерируется не на стороне джавы а в sql
    @Column(name = "id")
    private Integer id;

    @Column(name = "bank_document_id")
    private long bankDocumentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "passport_seria")
    private String passportSeria;

    @Column(name = "passport_number")
    private String passportNumber;
    @OneToMany(
            mappedBy = "customer",// у одного customer может быть много accounts
            fetch = FetchType.LAZY
            //fetch = FetchType.EAGER - загружать все сразу
            //fetch = FetchType.LAZY - загружать по мере необходимости. неудобно при закрытии сесси не сможем взять счет
            // тк данные не прогрузились
    )
    private List<Account> accounts;
    @ManyToMany
    @JoinTable(
            name = "customers_and_organizations",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private List<Organization> organizations;

    public Customer(Integer id, long bankDocumentId, String firstName, String lastName, Date birthday, String passportSeria, String passportNumber) {
        this.id = id;
        this.bankDocumentId = bankDocumentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
    }
}
