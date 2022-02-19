package cn.geekhall.starter.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HelloProperties
 *
 * @author yiny
 */
@ConfigurationProperties("geekhall.hello" )
public class HelloProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
