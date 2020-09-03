package com.epam.brest.courses.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapWebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }


    @Bean
    public XsdSchema projectSchema() {
        return new SimpleXsdSchema(new ClassPathResource("soap-xsd.xsd"));
    }

    @Bean(name = "soap-xsd")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema projectSchema) {

        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchema(projectSchema);
        definition.setLocationUri("/ws");
        definition.setPortTypeName("ProjectServicePort");
        definition.setTargetNamespace("http://epam.com/brest/courses/soap_api");
        return definition;
    }

}
