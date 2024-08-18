package com.xiaodao.wx.conch.route;

import com.xiaodao.wx.conch.dto.InstapaperAddDTO;
import com.xiaodao.wx.conch.service.InstapaperClient;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 链接消息处理器
 *
 * @author xiaodaojiang
 * @Classname LinkMsgTypeHandler
 * @Version 1.0.0
 * @Date 2024-07-23 22:53
 * @Created by xiaodaojiang
 */
@Service
public class LinkMsgTypeHandler extends AbstractHandler {

    @Autowired
    private InstapaperClient instapaperClient;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        final InstapaperAddDTO addDTO = new InstapaperAddDTO().setTitle(wxMessage.getTitle()).setSelection(wxMessage.getDescription()).setUrl(wxMessage.getUrl());
        if (instapaperClient.add(addDTO)) {
            return new TextBuilder().build(addDTO.getTitle() + "，添加成功！", wxMessage, wxMpService);
        }
        return new TextBuilder().build("添加失败", wxMessage, wxMpService);
    }
}
