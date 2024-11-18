package com.account.entities;

import com.account.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime date;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

    @ManyToOne
    @JoinColumn(name = "user_internal_id")
    private User user;



}
