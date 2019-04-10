package com.gene.library.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MaoLJ on 2018/8/6.
 * 转账通知
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RealmNotification extends RealmObject implements Serializable {

    @PrimaryKey
    private long timeStamp;

    private String time;

    private boolean isOut;

    private double balance;

    private String coinName;

    private String coinType;

    private String inAddress;

    private String outAddress;

    private boolean hasRead;

}
