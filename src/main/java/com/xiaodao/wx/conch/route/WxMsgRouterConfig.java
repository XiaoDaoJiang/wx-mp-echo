package com.xiaodao.wx.conch.route;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.LINK;

/**
 * 消息路由配置
 *
 * @author xiaodaojiang
 * @Classname WxMsgRouterConfig
 * @Version 1.0.0
 * @Date 2024-07-23 22:52
 * @Created by xiaodaojiang
 */
@Configuration
public class WxMsgRouterConfig {

    @Autowired
    private LogHandler logHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private UnsubscribeHandler unsubscribeHandler;

    @Autowired
    private LinkMsgTypeHandler linkMsgTypeHandler;

    @Autowired
    private MsgHandler msgHandler;


    @Bean(destroyMethod = "shutDownExecutorService")
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 链接消息
        newRouter.rule().async(false).msgType(LINK).handler(this.linkMsgTypeHandler).end();


        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        // 兜底
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}
