package com.wyj.test.mybatis.mapper;

import com.wyj.test.mybatis.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            " <if test=\"id != null\">where id &gt; #{id:VARCHAR} </if>" +
            " limit ${count}" +
            "</script>")
    List<UserInfo> list(@Param("id") Long id, @Param("count") int count);


//    @Select("<script>" +
//            "SELECT id as id, nick_name as nickName, alipay_user_id as alipayUserId FROM user_info" +
//            " where id in" +
//            " <foreach collection=\"ids\" item=\"item\" open=\"(\" close=\")\" separator=\",\">" +
//            " #{item}" +
//            " </foreach>" +
//            " <if test=\"name!=null\"> and nick_name like concat('%', #{name}, '%')</if>" +
//            "</script>")
//    List<UserInfo> list2(@Param("ids") List<Long> ids, @Param("name") String name);

    @Update("<script> UPDATE user_info" +
            "<if test=\"nickName != null\"> set nick_name=#{nickName}</if>" +
            "<if test=\"id != null\"> where id=#{id}</if>" +
            "</script>")
    int update(@Param("id")Long id, @Param("nickName")String name);

}
