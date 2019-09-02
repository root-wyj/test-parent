package com.wyj.test.mybatis.mapper;

import com.wyj.test.mybatis.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/30
 */
@Mapper
public interface UserInfoMapper {

    @Select("<script>" +
            "SELECT id as id, nick_name as nickName, alipay_user_id as alipayUserId FROM user_info" +
            " where id &gt; #{id} limit ${count}" +
            "</script>")
    List<UserInfo> list(@Param("id") long id, @Param("count") int count);

}
