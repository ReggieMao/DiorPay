package com.gene.library.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/7/23.
 * 行情
 */

@Data
public class Trade implements Serializable {

    private String chg_24H;

    private String currency_number;

    private String currency_price;

    private String deal_24H;

    private String name;

    private double price_cny;

}
