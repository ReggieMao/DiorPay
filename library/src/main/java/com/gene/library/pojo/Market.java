package com.gene.library.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/8/13.
 * 资讯
 */

@Data
public class Market implements Serializable {

    private List<Hot> hotList;

    private List<News> newsList;

    private List<Notice> noticeList;

    private int unreadNum;

}
