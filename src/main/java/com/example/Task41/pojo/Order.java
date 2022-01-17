package com.example.Task41.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    int id;
    enum OrderState {
        CANCELED, WAITING_FOR_PAYMENT, PAYMENT_COMPLETED
    }
    int price;
}
