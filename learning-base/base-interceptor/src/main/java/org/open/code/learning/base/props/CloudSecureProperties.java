package org.open.code.learning.base.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("cloud.secure")
public class CloudSecureProperties {

    /**
     * 开启鉴权规则
     */
    private Boolean enabled = false;

    /**
     * 开启令牌严格模式
     */
    private Boolean strictToken = true;

    /**
     * 开启请求头严格模式
     */
    private Boolean strictHeader = true;

    /**
     * 鉴权放行请求
     */
    private final List<String> skipUrl = new ArrayList<>();


    /**
     * 开启基础认证规则
     */
    private Boolean basicEnabled = true;

    /**
     * 基础认证配置
     */
    private final List<BasicSecure> basic = new ArrayList<>();

    /**
     * 开启签名认证规则
     */
    private Boolean signEnabled = true;

    /**
     * 签名认证配置
     */
    private final List<SignSecure> sign = new ArrayList<>();

    /**
     * 开启客户端规则
     */
    private Boolean clientEnabled = true;

    /**
     * 客户端配置
     */
    private final List<ClientSecure> client = new ArrayList<>();

}
