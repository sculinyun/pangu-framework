package cn.com.hbscjt.app.framework.sms.core.factory;

import cn.com.hbscjt.app.framework.sms.core.client.SmsClient;

/**
 * 短信客户端的工厂接口
 */
public interface SmsClientFactory {

    /**
     * 获得短信 Client
     *
     * @return 短信 Client
     */
    SmsClient getSmsClient();
}
