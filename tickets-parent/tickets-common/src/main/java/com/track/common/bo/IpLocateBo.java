package com.track.common.bo;

import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-25 22:27
 *
 * Ip信息
 */
@Data
public class IpLocateBo {

    private String retCode;

    private CityBo result;
}
