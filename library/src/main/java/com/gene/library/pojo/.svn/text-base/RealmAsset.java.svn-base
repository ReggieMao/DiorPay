package com.gene.library.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MaoLJ on 2018/8/6.
 * 资产
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RealmAsset extends RealmObject implements Serializable {

    @PrimaryKey
    private String coinName;

    private String address;

    private double balance;

    private String coinType;

    private double avalBalance;

}
