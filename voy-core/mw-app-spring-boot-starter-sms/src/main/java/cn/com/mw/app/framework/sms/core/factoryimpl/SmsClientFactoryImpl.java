package cn.com.mw.app.framework.sms.core.factoryimpl;

import cn.com.mw.app.framework.sms.core.cilentimpl.yunpian.YunpianSmsClient;
import cn.com.mw.app.framework.sms.core.client.SmsClient;
import cn.com.mw.app.framework.sms.core.enums.SmsChannelEnum;
import cn.com.mw.app.framework.sms.core.factory.SmsClientFactory;
import cn.com.mw.app.framework.sms.core.props.SmsChannelProperties;
import lombok.RequiredArgsConstructor;

/**
 * @author : ly
 * @date : 2023-09-22 15:19
 **/
@RequiredArgsConstructor
public class SmsClientFactoryImpl implements SmsClientFactory {
    private final SmsChannelProperties properties;

    @Override
    public SmsClient getSmsClient() {
        SmsChannelEnum channelEnum=SmsChannelEnum.getById(properties.getChannelId());
        switch (channelEnum) {
            case YUN_PIAN: return new YunpianSmsClient(properties);
        }
        return new YunpianSmsClient(properties);
    }

}
