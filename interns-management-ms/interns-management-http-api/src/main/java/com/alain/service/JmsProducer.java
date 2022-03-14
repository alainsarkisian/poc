package com.alain.service;

import com.alain.dto.json.Intern;
import com.alain.dto.mapper.InternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class JmsProducer {

    @Autowired
    @Qualifier("MyQueueProducerTemplate")
    private JmsTemplate jmsTemplate;

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    public void sendMessage(Intern internJson) {
        com.alain.dto.Intern internXml = InternMapper.MAPPER.fromJsonToXmlIntern(internJson);
        jmsTemplate.convertAndSend(internXml);
        logger.info("I just sent a new intern " +"First_Name : " + internXml.getFirstName() + " | Last_Name : " + internXml.getLastName() +" to the BROKER]");
    }
}
