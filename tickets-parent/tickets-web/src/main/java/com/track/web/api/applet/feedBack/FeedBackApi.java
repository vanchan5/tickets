package com.track.web.api.applet.feedBack;


import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.order.service.IOmFeedBackService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param  content
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/saveFeedBack/{content}")
    @ApiOperation("保存用户反馈信息")
    public JsonViewData saveFeedBack(@PathVariable String content){

        service.saveFeedBack(content);
        return setJsonViewData(ResultCode.SUCCESS,"提交成功");
    }

}
