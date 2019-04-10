package com.gene.library.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MaoLJ on 2018/8/6.
 * 币种
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RealmCoin extends RealmObject implements Serializable {

    @PrimaryKey
    private String coinName;

    private boolean add;

    private boolean show;

}
