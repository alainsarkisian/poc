package com.alain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
//@ConditionalOnBean(value = { ConnectionFactory.class })
public class JmsConfig {
    @Autowired
    private ConnectionFactory connectionFactory;

    /*
        Produce for post request
    */
    @Bean("PostRequestProducer")
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
    @Bean("GetByIdRequestProducer")
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

    /*
        Consume for get response

    @Bean("GetByIdResponseConsumer")
    public DefaultJmsListenerContainerFactory myQueueConsumerJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.alain.dto");
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);
        converter.setTargetType(MessageType.TEXT);
        factory.setMessageConverter(converter);
        return factory;
    }

     */
}
