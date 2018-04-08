package com.trump.auction.cust.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserRelation implements Serializable{
    private Integer pid;

    private Integer sid;

    private Date createTime;
}