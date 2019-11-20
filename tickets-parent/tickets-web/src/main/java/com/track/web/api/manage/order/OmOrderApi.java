package com.track.web.api.manage.order;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.order.search.OrderRefundDto;
import com.track.data.dto.manage.order.search.SearchAccountLogDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.vo.manage.order.AccountLogVo;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmAccountLogService;
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

    @Autowired
    private IOmAccountLogService omAccountLogService;


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

    /**
     * @Author yeJH
     * @Date 2019/11/13 14:12
     * @Description 查询系统流水
     *
     * @Update yeJH
     *
     * @param  searchAccountLogDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.AccountLogVo>>
     **/
    @ApiOperation(value = "查询系统流水", notes = "根据关联订单号，流水类型，流水发生时间，用户昵称等条件查询")
    @PostMapping("/searchAccountLog")
    public JsonViewData<PageInfo<AccountLogVo>> searchAccountLog(
            @ApiParam(required = true, name = "searchAccountLogDto", value = "查询条件")
            @Validated @RequestBody SearchAccountLogDto searchAccountLogDto) {

        PageInfo<AccountLogVo> accountLogVoPageInfo = omAccountLogService.searchAccountLog(searchAccountLogDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                accountLogVoPageInfo);

    }

    /**
     * @Author yeJH
     * @Date 2019/11/13 14:12
     * @Description 根据订单号，单个或者批量退款，也可根据场次将所有订单退款
     *
     * @Update yeJH
     *
     * @param  orderRefundDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.AccountLogVo>>
     **/
    @ApiOperation(value = "订单退款", notes = "根据订单号，单个或者批量退款，也可根据场次将所有订单退款")
    @PostMapping("/orderRefund")
    public JsonViewData orderRefund(
            @ApiParam(required = true, name = "orderRefundDto", value = "单个或批量订单号，或者某个场次")
            @Validated @RequestBody OrderRefundDto orderRefundDto) {

        //PageInfo<AccountLogVo> accountLogVoPageInfo = omAccountLogService.searchAccountLog(orderRefundDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功");

    }

}
