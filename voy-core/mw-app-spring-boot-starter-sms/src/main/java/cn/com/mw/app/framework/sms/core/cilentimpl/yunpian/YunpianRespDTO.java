package cn.com.mw.app.framework.sms.core.cilentimpl.yunpian;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 云片消息接收 Response DTO
 */
@Data
public class YunpianRespDTO {

    /**
     * 0代表发送成功，其他code代表出错
     */
    private Integer code;
    /**
     * 例如""发送成功""，或者相应错误信息
     */
    private String msg;
    /**
     * 发送成功短信的计费条数(计费条数：70个字一条，超出70个字时按每67字一条计费)
     */
    private Integer count;

    /**
     * 扣费金额，单位：元，类型：双精度浮点型/double
     */
    private BigDecimal fee;
    /**
     * 计费单位；例如：“RMB”
     */
    private String unit;

    /**
     * 发送手机号
     */
    private String mobile;
    /**
     * 短信id，64位整型， 对应Java和C#的long，不可用int解析
     */
    private Long sid;
}
