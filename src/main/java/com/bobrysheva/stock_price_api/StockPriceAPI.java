package com.bobrysheva.stock_price_api;

import liquibase.Liquibase;
import liquibase.sdk.supplier.resource.ResourceSupplier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;

@SpringBootApplication
public class StockPriceAPI {
    public static void main(String[] args) {
        SpringApplication.run(StockPriceAPI.class, args);
    }
}
