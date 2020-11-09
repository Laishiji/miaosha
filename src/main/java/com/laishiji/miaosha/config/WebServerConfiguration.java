package com.laishiji.miaosha.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * 当Spring容器内没有TomcatEmbeddedServletContainerFactory这个bean时，会加载此bean到容器
 */
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    /**
     * 使用对应工厂类提供给我们的接口定制化Tomcat
     * @param factory
     */
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();

                //定制化keepAliveTimeOut,30s内没有请求则服务端断开TCP连接
                protocol.setKeepAliveTimeout(30000);

                //定制化maxKeepAliveRequests
                protocol.setMaxKeepAliveRequests(10000);
            }
        });
    }
}
