package cn.com.hbscjt.app.framework.sms.core.cilentimpl.yunpian;

import cn.com.hbscjt.app.framework.common.pojo.CommonResult;
import cn.com.hbscjt.app.framework.common.util.JsonUtils;
import cn.com.hbscjt.app.framework.sms.core.client.SmsClient;
import cn.com.hbscjt.app.framework.sms.core.dto.SmsRespDTO;
import cn.com.hbscjt.app.framework.sms.core.enums.SmsChannelEnum;
import cn.com.hbscjt.app.framework.sms.core.props.SmsChannelProperties;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static cn.com.hbscjt.app.framework.common.pojo.CommonResult.success;

/**
 * 云片短信客户端的实现类
 */
@Slf4j
@RequiredArgsConstructor
public class YunpianSmsClient implements SmsClient {

    private final SmsChannelProperties properties;

    @Override
    public Integer getId() {
        return SmsChannelEnum.YUN_PIAN.getId();
    }

    @Override
    public CommonResult<SmsRespDTO> sendSms(String mobile,String text) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", properties.getApiKey());
        params.put("text", text);
        params.put("mobile", mobile);
        String resp=HttpUtil.post("https://sms.yunpian.com/v2/sms/single_send.json",params);
        YunpianRespDTO respDTO=JsonUtils.parseObject(resp,YunpianRespDTO.class);
        if(respDTO.getCode()==0){
            SmsRespDTO smsRespDTO=new SmsRespDTO();
            smsRespDTO.setSuccess(Boolean.TRUE);
            smsRespDTO.setMobile(mobile);
            smsRespDTO.setSerialNo(String.valueOf(respDTO.getSid()));
            return success(smsRespDTO);
        }else {
            SmsRespDTO smsRespDTO=new SmsRespDTO();
            smsRespDTO.setSuccess(Boolean.FALSE);
            smsRespDTO.setMobile(mobile);
            smsRespDTO.setErrorCode(smsRespDTO.getErrorCode());
            smsRespDTO.setErrorMsg(smsRespDTO.getErrorMsg());
            smsRespDTO.setSerialNo(String.valueOf(respDTO.getSid()));
            return success(smsRespDTO);
        }
    }
}
