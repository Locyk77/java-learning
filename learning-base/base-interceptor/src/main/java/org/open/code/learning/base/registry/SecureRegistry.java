package org.open.code.learning.base.registry;

import lombok.Data;
import lombok.Getter;
import org.open.code.learning.base.props.BasicSecure;
import org.open.code.learning.base.props.SignSecure;
import org.open.code.learning.base.provider.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
@Data
public class SecureRegistry {
    /**
     * 是否开启鉴权
     */
    private boolean enabled = true;

    /**
     * 开启令牌严格模式
     */
    private boolean strictToken = true;

    /**
     * 开启请求头严格模式
     */
    private boolean strictHeader = true;

    /**
     * 是否开启授权
     */
    private boolean authEnabled = true;

    /**
     * 是否开启基础认证
     */
    private boolean basicEnabled = true;

    /**
     * 是否开启签名认证
     */
    private boolean signEnabled = true;

    /**
     * 是否开启客户端认证
     */
    private boolean clientEnabled = true;

    /**
     * 默认放行规则
     */
    private final List<String> defaultExcludePatterns = new ArrayList<>();

    /**
     * 自定义放行规则
     */
    private final List<String> excludePatterns = new ArrayList<>();


    /**
     * 基础认证集合
     */
    @Getter
    private final List<BasicSecure> basicSecures = new ArrayList<>();

    /**
     * 签名认证集合
     */
    @Getter
    private final List<SignSecure> signSecures = new ArrayList<>();

    public SecureRegistry() {
        this.defaultExcludePatterns.add("/actuator/health/**");
        this.defaultExcludePatterns.add("/v3/api-docs/**");
        this.defaultExcludePatterns.add("/swagger-ui/**");
        this.defaultExcludePatterns.add("/oauth/**");
        this.defaultExcludePatterns.add("/feign/client/**");
        this.defaultExcludePatterns.add("/process/resource-view");
        this.defaultExcludePatterns.add("/process/diagram-view");
        this.defaultExcludePatterns.add("/manager/check-upload");
        this.defaultExcludePatterns.add("/tenant/info");
        this.defaultExcludePatterns.add("/static/**");
        this.defaultExcludePatterns.add("/assets/**");
        this.defaultExcludePatterns.add("/error");
        this.defaultExcludePatterns.add("/favicon.ico");
    }

    /**
     * 设置单个放行api
     */
    public SecureRegistry skipUrl(String pattern) {
        return this.excludePathPattern(pattern);
    }

    /**
     * 设置放行api集合
     */
    public SecureRegistry skipUrls(String... patterns) {
        return this.excludePathPatterns(patterns);
    }

    /**
     * 设置放行api集合
     */
    public void skipUrls(List<String> patterns) {
        this.excludePathPatterns(patterns);
    }

    /**
     * 设置单个放行api
     */
    public SecureRegistry excludePathPattern(String pattern) {
        this.excludePatterns.add(pattern);
        return this;
    }

    /**
     * 设置放行api集合
     */
    public SecureRegistry excludePathPatterns(String... patterns) {
        this.excludePatterns.addAll(Arrays.asList(patterns));
        return this;
    }

    /**
     * 设置放行api集合
     */
    public void excludePathPatterns(List<String> patterns) {
        this.excludePatterns.addAll(patterns);
    }


    /**
     * 设置基础认证
     */
    public SecureRegistry addBasicPattern(HttpMethod method, String pattern, String username, String password) {
        this.basicSecures.add(new BasicSecure(method, pattern, username, password));
        return this;
    }

    /**
     * 设置基础认证集合
     */
    public SecureRegistry addBasicPatterns(List<BasicSecure> basicSecures) {
        this.basicSecures.addAll(basicSecures);
        return this;
    }

    /**
     * 设置签名认证
     */
    public SecureRegistry addSignPattern(HttpMethod method, String pattern, String crypto) {
        this.signSecures.add(new SignSecure(method, pattern, crypto));
        return this;
    }

    /**
     * 设置签名认证集合
     */
    public SecureRegistry addSignPatterns(List<SignSecure> signSecures) {
        this.signSecures.addAll(signSecures);
        return this;
    }

    /**
     * 设置是否开启令牌严格模式
     */
    public SecureRegistry strictToken(boolean strictToken) {
        this.strictToken = strictToken;
        return this;
    }

    /**
     * 设置是否开启请求头严格模式
     */
    public SecureRegistry strictHeader(boolean strictHeader) {
        this.strictHeader = strictHeader;
        return this;
    }

    /**
     * 开启令牌严格模式
     */
    public SecureRegistry strictTokenEnabled() {
        this.strictToken = true;
        return this;
    }

    /**
     * 关闭令牌严格模式
     */
    public SecureRegistry strictTokenDisabled() {
        this.strictToken = false;
        return this;
    }

    /**
     * 开启请求头严格模式
     */
    public SecureRegistry strictHeaderEnabled() {
        this.strictHeader = true;
        return this;
    }

    /**
     * 关闭请求头严格模式
     */
    public SecureRegistry strictHeaderDisabled() {
        this.strictHeader = false;
        return this;
    }

    /**
     * 开启鉴权
     */
    public SecureRegistry enabled() {
        this.enabled = true;
        return this;
    }

    /**
     * 关闭鉴权
     */
    public SecureRegistry disabled() {
        this.enabled = false;
        return this;
    }

    /**
     * 开启授权
     */
    public SecureRegistry authEnabled() {
        this.authEnabled = true;
        return this;
    }

    /**
     * 关闭授权
     */
    public SecureRegistry authDisabled() {
        this.authEnabled = false;
        return this;
    }

    /**
     * 开启基础认证
     */
    public SecureRegistry basicEnabled() {
        this.basicEnabled = true;
        return this;
    }

    /**
     * 关闭基础认证
     */
    public SecureRegistry basicDisabled() {
        this.basicEnabled = false;
        return this;
    }

    /**
     * 开启签名认证
     */
    public SecureRegistry signEnabled() {
        this.signEnabled = true;
        return this;
    }

    /**
     * 关闭签名认证
     */
    public SecureRegistry signDisabled() {
        this.signEnabled = false;
        return this;
    }

    /**
     * 开启客户端认证
     */
    public SecureRegistry clientEnabled() {
        this.clientEnabled = true;
        return this;
    }

    /**
     * 关闭客户端认证
     */
    public SecureRegistry clientDisabled() {
        this.clientEnabled = false;
        return this;
    }

}
