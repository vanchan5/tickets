package com.track.data.dto.applet.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @Description 小程序获取用户手机号码访问getPhoneNumber返回参数
 * @since 2019/11/22 11:36
 */
@Data
@ApiModel(description = "订单下单提交参数")
public class GetPhoneNumberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "包括敏感数据在内的完整用户信息的加密数据")
    @NotNull(message = "encryptedData参数不能为空")
    private String encryptedData;

    @ApiModelProperty(value = "加密算法的初始向量")
    @NotNull(message = "iv参数不能为空")
    private String iv;

    @ApiModelProperty(value = "登录code")
    @NotNull(message = "code参数不能为空")
    private String code;

}
