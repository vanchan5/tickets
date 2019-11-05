package com.track.web.api.manage.order;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 支付订单 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "后台_订单管理接口")
@RestController
@RequestMapping("/manage/order")
@Slf4j
public class OmOrderApi extends BaseWeb {

    @Autowired
    private IOmOrderService service;


    /**
     * @Author yeJH
     * @Date 2019/10/29 16:02
     * @Description 查询平台订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.ManageOrderListVo>>
     **/
    @ApiOperation(value = "查询平台订单列表", notes = "根据下单时间，订单号，订单状态，购买人手机以及购买场次等条件查询")
    @PostMapping("/searchOrderList")
    public JsonViewData<PageInfo<ManageOrderListVo>> searchOrderList(
            @ApiParam(required = true, name = "searchOrderDto", value = "查询条件")
            @Validated @RequestBody SearchOrderDto searchOrderDto) {

        PageInfo<ManageOrderListVo> manageOrderListVoPageInfo = service.searchOrderList(searchOrderDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                manageOrderListVoPageInfo);

    }

}
