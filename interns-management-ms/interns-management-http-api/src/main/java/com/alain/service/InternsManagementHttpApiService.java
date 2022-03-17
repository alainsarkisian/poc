package com.alain.service;


import com.alain.dto.json.Intern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

@Component
@Service
public class InternsManagementHttpApiService {

    @Autowired
    private JmsProducer jmsProducer;
    final static Logger logger = Logger.getLogger(String.valueOf(InternsManagementHttpApiService.class));


    public List<Intern> getAllInterns() {
        return null;
    }

    public Intern getAnInternByFirstName(String firstName){
        return null;
    }

    /*
    Send to the PostQueue the XML intern that has to be saved in DB
     */
    public void addAnIntern(Intern internJson) {
        this.jmsProducer.sendMessage(internJson);
    }

    /*
    Send to the GetQueue the XML intern with only an ID that has to be found in DB and returned to the client
     */
    public Intern getAnInternById(Long id) {
        com.alain.dto.Intern internXml = new com.alain.dto.Intern();
        internXml.setIdIntern(BigInteger.valueOf(id));
        this.jmsProducer.sendMessageForGetById(internXml);
        return null;
    }

    public void deleteAnIntern(Long idIntern) {
    }

    public void updateAnIntern(Intern intern) {
    }
}
