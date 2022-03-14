package com.alain.service;

import com.alain.dto.Intern;
import com.alain.mapper.InternMapper;
import com.alain.repository.InternsManagementServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Component
@Service
public class InternsManagementService {
    @Autowired
    private InternsManagementServiceRepository internsManagementServiceRepository;

    final static Logger logger = Logger.getLogger(String.valueOf(InternsManagementService.class));

   @JmsListener(destination = "AnInternByFirstNameQueue", containerFactory = "MyQueueConsumer")
    public void receive(@Payload Intern message) {
        logger.info("I just Received  " + "First_Name : " + message.getFirstName() + " | Last_Name : " + message.getLastName());
       com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(message);
       this.addAnIntern2(internEntity);
       logger.info("SAVE IN DB  " + internEntity.toString());

   }


    public void addAnIntern(Intern internXml){
        com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        this.internsManagementServiceRepository.save(internEntity);
    }

    public void addAnIntern2(com.alain.model.Intern internEntity){
        this.internsManagementServiceRepository.save(internEntity);
    }



}