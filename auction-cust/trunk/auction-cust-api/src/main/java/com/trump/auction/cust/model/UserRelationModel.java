package com.trump.auction.cust.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserRelationModel implements Serializable{
    private Integer pid;

    private Integer sid;

    private Date createTime;
}