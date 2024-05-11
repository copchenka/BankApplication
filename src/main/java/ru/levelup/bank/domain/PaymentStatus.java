package ru.levelup.bank.domain;

import lombok.Getter;

public enum PaymentStatus {
    //у enum приватный конструктор.инициализируется при старте приложения
    //имеет свои методы
    //sqlcode can cansel-доп поля и атрибуты для работы с энамом
    //можно наследоваться от интерфейса и для каждого объекта переопределить метод(или один для всех)
    NEW(1, true),
    CONFIRMED(2, true),
    DONE(3, false),
    FAILED(4, false);

    @Getter
    private int sqlCode;
    private boolean canCancel;

    PaymentStatus(int sqlCode, boolean canCancel) {
        this.sqlCode = sqlCode;
    }

    public PaymentStatus findEnumOrNull(String s) {
        for (PaymentStatus status : values()) {
            if (status.name().equals((s.toUpperCase()))) {
                return status;
            }
        }
        return null;
    }
}
