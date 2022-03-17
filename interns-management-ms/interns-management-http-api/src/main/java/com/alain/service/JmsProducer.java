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

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    @Autowired
    @Qualifier("PostRequestProducer")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("GetByIdRequestProducer")
    private JmsTemplate jmsTemplateForGet;

    /*
        Produce for post
     */
    public void sendMessage(Intern internJson) {
        com.alain.dto.Intern internXml = InternMapper.MAPPER.fromJsonToXmlIntern(internJson);
        jmsTemplate.convertAndSend(internXml);
        logger.info("[PRODUCING : POST] {" +
                "First_Name : " + internXml.getFirstName() +
                ", Last_Name : " + internXml.getLastName() +"}");
    }

    /*
        Produce for get
     */
    public void sendMessageForGetById(com.alain.dto.Intern internXml) {
        jmsTemplateForGet.convertAndSend(internXml);
        logger.info("[PRODUCING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() + "}");
    }
}
