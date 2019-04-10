package com.gene.library.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/7/27.
 * 钱包
 */

@Data
public class Wallet implements Serializable {

    private double balance;

    private List<Asset> list;

}
