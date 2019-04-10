package com.gene.library.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/7/27.
 * 余额
 */

@Data
public class Balance implements Serializable {

    private double beforeChangeBalance; //交易前金额  若为0 则不显示

    private int blockConformations;

    private String blockDate; //区块生成时间

    private int blockHeight; //区块高度

    private String bookCode; //币种 2是SAS 10是BZF 11是DOB 20是ETH

    private String changeAction; //交易类型

    private double changeValue; //交易金额

    private String contractAddress;

    private String createDate; //数据创建时间

    private int id;

    private String receiveAddress; //接收地址

    private String remark;

    private String sendAddress; //发送地址

    private double serviceFee; //手续费

    private int status;

    private String transationId; //交易id

    private String updateDate;

    private String userId;

    private String index;

}
