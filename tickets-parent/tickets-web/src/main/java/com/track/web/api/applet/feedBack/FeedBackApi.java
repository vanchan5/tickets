package com.track.web.api.applet.feedBack;


import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.applet.feedBack.SaveFeedBackDto;
import com.track.order.service.IOmFeedBackService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 意见反馈表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "小程序_意见反馈接口")
@RestController
@RequestMapping("/applet/feedBack")
@Slf4j
public class FeedBackApi extends BaseWeb {

    @Autowired
    private IOmFeedBackService service;

    /**
     * @Author chauncy
     * @Date 2019-10-30 22:22
     * @Description //保存用户反馈信息
     *
     * @Update chauncy
     *
     * @param  saveFeedBackDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @PostMapping("/saveFeedBack")
    @ApiOperation("保存用户反馈信息")
    public JsonViewData saveFeedBack(@RequestBody @ApiParam(required = true,name = "saveFeedBackDto",value = "保存反馈信息")
                                     @Validated SaveFeedBackDto saveFeedBackDto){

        service.saveFeedBack(saveFeedBackDto);
        return setJsonViewData(ResultCode.SUCCESS,"提交成功");
    }

}
