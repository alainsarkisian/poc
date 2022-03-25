package com.alain.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
/*
    If we want to use a component class instead of a bean :
    JmsProducerjava : line 19(refers to bean in JmsConfig in comment, line 21 used
    If we prefer a bean : line 19 used and line 21 in comment
*/
//@Component
public class JmsTemplateExtender extends JmsTemplate {
    JmsTemplateExtender(ConnectionFactory connectionFactory){
        super();
        this.setDefaultDestinationName("PostRequestQueue");
        this.setConnectionFactory(connectionFactory);
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.alain.dto");
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);
        converter.setTargetType(MessageType.TEXT);
        this.setMessageConverter(converter);
    }
}
