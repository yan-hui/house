package lone1.wolf.house.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/27 10:52
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {
    private Integer connectTimeOut = 1000;
    private Integer scoketTimeOut = 10000;

    private String agent = "agent";
    private Integer maxConnPerRoute = 10;
    private Integer maxConnTotal = 50;

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Integer getScoketTimeOut() {
        return scoketTimeOut;
    }

    public void setScoketTimeOut(Integer scoketTimeOut) {
        this.scoketTimeOut = scoketTimeOut;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(Integer maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public Integer getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }
}
