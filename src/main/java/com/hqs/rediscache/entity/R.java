package com.hqs.rediscache.entity;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class R {
    private Integer code;
    private Object data;
    private String msg;
}
