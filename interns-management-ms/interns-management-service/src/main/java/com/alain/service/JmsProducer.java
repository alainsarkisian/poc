package com.alain.service;

import com.alain.mapper.InternMapper;
import com.alain.model.Intern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class JmsProducer {

    @Autowired
    @Qualifier("GetByIdResponseProducer")
    private JmsTemplate jmsTemplate;

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    public void sendMessage(Intern intern) {
        com.alain.dto.Intern internXml = InternMapper.MAPPER.fromEntityInternToXml(intern);
        jmsTemplate.convertAndSend(internXml);
        logger.info("[PRODUCING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern()+ ", " +
                "First_Name : " + internXml.getFirstName() + ", " +
                "Last_Name : " + internXml.getLastName() +"}");
    }
}
