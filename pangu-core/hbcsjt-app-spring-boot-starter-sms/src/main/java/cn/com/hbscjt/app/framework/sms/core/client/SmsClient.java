package cn.com.hbscjt.app.framework.sms.core.client;


import cn.com.hbscjt.app.framework.common.pojo.CommonResult;
import cn.com.hbscjt.app.framework.sms.core.dto.SmsRespDTO;

/**
 * 短信客户端，用于对接各短信平台的 SDK，实现短信发送等功能
 */
public interface SmsClient {

    /**
     * 获得渠道编号
     *
     * @return 渠道编号
     */
    Integer getId();

    /**
     * 发送消息
     * @param mobile 手机号
     * @param text 短信发送内容文本
     * @return 短信发送结果
     */
    CommonResult<SmsRespDTO> sendSms(String mobile,String text);

}
