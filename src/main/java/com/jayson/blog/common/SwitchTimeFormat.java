package com.jayson.blog.common;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Jayson_Y
 * @date: 2024/8/2
 * @project: blog-system-backend
 */
@Data
public class SwitchTimeFormat {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String switchTimeFormatToString(Date time) {
        return sdf.format(time);
    }

}
