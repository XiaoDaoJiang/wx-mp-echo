package com.xiaodao.wx.conch.route;

import cn.hutool.json.JSONUtil;
import com.xiaodao.wx.conch.service.InstapaperClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

@Slf4j
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    private InstapaperClient instapaperClient;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            // TODO 可以选择将消息保存到本地
        }

        // 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        final String content = wxMessage.getContent();
        if (StringUtils.startsWithAny(content, "https://", "http://")) {
            final boolean added = instapaperClient.addUrl(content);
            if (added) {
                return new TextBuilder().build(content + "，添加成功！", wxMessage, weixinService);
            }
        }

        // TODO 组装回复消息
        String response = "🐶收到信息内容：" + JSONUtil.toJsonPrettyStr(wxMessage);

        return new TextBuilder().build(response, wxMessage, weixinService);

    }

}
