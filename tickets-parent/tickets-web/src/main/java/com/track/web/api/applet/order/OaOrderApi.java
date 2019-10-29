package com.track.web.api.applet.order;

import com.track.order.service.IOmOrderService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/10/29 17:17
 * <p>
 * 小程序订单接口
 */
@Api(tags = "小程序_订单接口")
@RestController
@RequestMapping("/applet/order")
@Slf4j
public class OaOrderApi extends BaseWeb {

    @Autowired
    private IOmOrderService service;


    /**
     * @Author yeJH
     * @Date 2019/10/29 16:02
     * @Description 查询订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.ManageOrderListVo>>
     **/
    /*@ApiOperation(value = "查询订单列表", notes = "根据下单时间，订单号，订单状态，购买人手机以及购买场次等条件查询")
    @PostMapping("/searchOrderList")
    public JsonViewData<PageInfo<ManageOrderListVo>> searchOrderList(
            @ApiParam(required = true, name = "searchOrderDto", value = "查询条件")
            @Validated @RequestBody SearchOrderDto searchOrderDto) {

        PageInfo<ManageOrderListVo> manageOrderListVoPageInfo = service.searchOrderList(searchOrderDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                manageOrderListVoPageInfo);

    }*/

}

