package com.alain.service;

import com.alain.dto.Intern;
import com.alain.mapper.InternMapper;
import com.alain.repository.InternsManagementServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Component
@Service
public class InternsManagementService {

    @Autowired
    private InternsManagementServiceRepository internsManagementServiceRepository;

    @Autowired
    private JmsProducer jmsProducer;

    final static Logger logger = Logger.getLogger(String.valueOf(InternsManagementService.class));

    /*
        Input : an XML Intern
        Action1 : map the XML intern into Entity Intern
        Action2 : save this Entity Intern into DB
     */
    public void addAnInternFromXmlToEntity(Intern internXml){
        com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        this.internsManagementServiceRepository.save(internEntity);
    }

    /*
        Input : an Entity Intern
        Action : save this Entity Intern into DB
    */
    public void addAnInternFromEntity(com.alain.model.Intern internEntity){
        this.internsManagementServiceRepository.save(internEntity);
    }

    /*
        Consume from Post request Queue
     */
   @JmsListener(destination = "PostRequestQueue", containerFactory = "PostRequestConsumer")
    public void receiveFromPostRequest(@Payload Intern internXml) {
       logger.info("[CONSUMING : GET] {" +
               ", First_Name : " + internXml.getFirstName() +
               ", Last_Name : " + internXml.getLastName() +"}");
       com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
       this.addAnInternFromEntity(internEntity);
       logger.info("[SAVING IN DB] : " + internEntity.toString());
   }

   /*
        Input : an ID Intern
        Action : Find the Intern by ID in DB
        Output : return the result of the DB request
    */
   public Optional<com.alain.model.Intern> getAnInternById(Long id){
        return this.internsManagementServiceRepository.findById(id);
   }

    /*
        Consume from Get request Queue
    */
    @JmsListener(destination = "GetByIdRequestQueue", containerFactory = "GetByIdConsumer")
    public void receiveFromGetIdRequest(@Payload Intern internXml) {
        logger.info("[CONSUMING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() + "}");
        com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        Optional<com.alain.model.Intern> internResultGetById = this.getAnInternById(internEntity.getIdIntern());
        internResultGetById.ifPresent(intern -> logger.info("[SEARCHING IN DB] : FOUND" +
                ", First_Name : " + intern.getFirstName() +
                ", Last_Name : " + intern.getLastName() + "}"));
    }




    /*
    Produce
        public Intern getAnInternById(Long id){
       return null;
    }

     */


}