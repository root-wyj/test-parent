package com.wyj.test.mybatis_plus.simple;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("non_asset_item_quantity")
public class NonAssetItemQuantity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer itemId;

    private String itemType;

    private String businessType;

    private Integer onHandAmount;

    private String cityName;

    private String locationName;

    private String createCode;

    private String updateCode;

    private Date createTime;

    private Date updateTime;

}