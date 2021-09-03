package com.track.login.details.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * 继承系统用户实体类，用于给实现了UserDetails接口的SecurityUserDetails
 * 有参构造函数传参
 */
@Data
@Accessors(chain = true)
public class UserInfoBo extends UmUserBo{

}
