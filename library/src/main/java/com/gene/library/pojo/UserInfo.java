package com.gene.library.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/8/3.
 * 个人信息
 */

@Data
public class UserInfo implements Serializable {

    private String loginAccount;

    private String regTime;

    private String nickName;

    private String walletAddress;

    private String userId;

    private String portraitImagUrl;

}
