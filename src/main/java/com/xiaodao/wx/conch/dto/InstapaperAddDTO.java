package com.xiaodao.wx.conch.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiaodaojiang
 * @Classname InstapaperAddDTO
 * @Version 1.0.0
 * @Date 2024-07-16 23:03
 * @Created by xiaodaojiang
 */
@Accessors(chain = true)
@Data
public class InstapaperAddDTO {
    private String url;
    private String title;
    private String selection;
}
