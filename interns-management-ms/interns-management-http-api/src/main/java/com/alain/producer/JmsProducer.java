package com.alain.producer;

import com.alain.dto.json.Intern;
import com.alain.mapper.InternMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JmsProducer {

    final Logger logger = Logger.getLogger(String.valueOf(JmsProducer.class));

    @Qualifier("PostRequestProducer")
    private final JmsTemplate jmsTemplate;

    ///private final JmsTemplateExtender jmsTemplate;
    @Qualifier("GetByIdRequestProducer")
    private final JmsTemplate jmsTemplateForGet;

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
