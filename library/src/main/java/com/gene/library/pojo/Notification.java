package com.gene.library.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/8/9.
 * 转账通知
 */

@Data
public class Notification implements Serializable {

    private long timeStamp;

    private String time;

    private String address;

    private String coinName;

    private double count;

    private String walletType;

    private boolean isOut;

    private boolean hasRead;

}
