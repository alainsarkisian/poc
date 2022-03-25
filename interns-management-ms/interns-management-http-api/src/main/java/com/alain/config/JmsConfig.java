package com.alain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
@RequiredArgsConstructor
//@ConditionalOnBean(value = { ConnectionFactory.class })
public class JmsConfig {

    private final ConnectionFactory connectionFactory;

    /*
        Produce for post request
    */
    @Bean
    @Qualifier("PostRequestProducer")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestinationName("PostRequestQueue");
        jmsTemplate.setConnectionFactory(connectionFactory);
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.alain.dto");
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);
        converter.setTargetType(MessageType.TEXT);
        jmsTemplate.setMessageConverter(converter);
        return jmsTemplate;
    }

    /*
    Produce for get request
    */
    @Bean
    @Qualifier("GetByIdRequestProducer")
    public JmsTemplate jmsTemplateForGet() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestinationName("GetByIdRequestQueue");
        jmsTemplate.setConnectionFactory(connectionFactory);
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.alain.dto");
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);
        converter.setTargetType(MessageType.TEXT);
        jmsTemplate.setMessageConverter(converter);
        return jmsTemplate;
    }
}
