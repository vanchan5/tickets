package com.track.common.utils.wetch.applet;

import com.track.common.utils.JSONUtils;
import com.track.data.bo.applet.CodeToSessionBo;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.mapper.ticket.OmSceneRelGradeMapper;
import com.track.order.service.IOmOrderService;
import com.track.web.StartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yeJH
 * @description:
 * @since 2019/10/17 11:37
 */
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class WxAppletUtilTest {

    @Autowired
    private WxAppletBo wxAppletConfig;

    @Autowired
    private IOmOrderService omOrderService;

    @Test
    public void codeToSession() {
        try {
            String data = WxAppletUtil.codeToSession("033pKJB42gQj0P0CBeB42CfPB42pKJBb");
            data = data.replace("session_key", "sessionKey")
                    .replace("openid", "openId")
                    .replace("errcode", "errCode")
                    .replace("errmsg", "errMsg");

            System.out.println(data);
            CodeToSessionBo nReq = JSONUtils.toBean(data, CodeToSessionBo.class);
            System.out.println(nReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getRemainingSum() {
        OrderSubmitDto orderSubmitDto = new OrderSubmitDto();
        orderSubmitDto.setGradeId(1L);
        orderSubmitDto.setSceneId(2L);
        Long  remainingSum = omOrderService.submit(orderSubmitDto, null);

    }
}
