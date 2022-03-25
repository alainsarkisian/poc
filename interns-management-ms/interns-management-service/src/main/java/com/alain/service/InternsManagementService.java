package com.alain.service;

import com.alain.dto.Intern;
import com.alain.mapper.InternMapper;
import com.alain.repository.InternsManagementServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.logging.Logger;

@Component
@Service
@RequiredArgsConstructor
public class InternsManagementService {

    private final InternsManagementServiceRepository internsManagementServiceRepository;

    private final JmsProducer jmsProducer;

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
       logger.info("[CONSUMING : POST] {" +
               "First_Name : " + internXml.getFirstName() +
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
    @JmsListener(destination = "GetByIdRequestQueue", containerFactory = "GetByIdRequestConsumer")
    public void receiveFromGetIdRequest(@Payload Intern internXml) {
        /*
        Receiving
         */
        logger.info("[CONSUMING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() + "}");
        /*
        Mapping
         */
        com.alain.model.Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);

        /*
        Requesting DB
         */
        try {
            Optional<com.alain.model.Intern> internResultGetById = this.getAnInternById(internEntity.getIdIntern());
            if (internResultGetById.isPresent()) {
                logger.info("[SEARCHING IN DB] : FOUND" +
                        ", First_Name : " + internResultGetById.get().getFirstName() +
                        ", Last_Name : " + internResultGetById.get().getLastName() + "}");
                /*
                    Sending result to the response queue
                */
                this.jmsProducer.sendMessage(internResultGetById.get());
            }
            else {
                logger.warning("ID DOES NOT EXIST");
                return;
            }
        } catch (Exception e) {
            logger.warning("ID DOES NOT EXIST");
            return;
        }


    }

    /*
    Produce
        public Intern getAnInternById(Long id){
       return null;
    }

     */
}