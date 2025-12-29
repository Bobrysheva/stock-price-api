package com.bobrysheva.stock_price_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "stockDataList")
@Table(name = "tickers")

public class Ticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticker_id")
    private Long id;

    @Column(name = "ticker_symbol", unique = true, nullable = false)
    private String symbol;

    @OneToMany(mappedBy = "ticker", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<StockData> stockDataList = new HashSet<>();
}
