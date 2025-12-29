package com.bobrysheva.stock_price_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users", "ticker"})
@Table(name = "stock_data")
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_data_id")
    private Long id;

    @Column(name = "stock_data_trade_date")
    private LocalDateTime date;

    @Column(name = "stock_data_open")
    private Double open;

    @Column(name = "stock_data_close")
    private Double close;

    @Column(name = "stock_data_high")
    private Double high;

    @Column(name = "stock_data_low")
    private Double low;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id", nullable = false)
    private Ticker ticker;

    @ManyToMany(mappedBy = "loadedDatas", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
