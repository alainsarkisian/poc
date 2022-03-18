package alain.internmanagementworker.service;

import alain.internmanagementworker.mapper.InternMapper;
import alain.internmanagementworker.model.Intern;
import alain.internmanagementworker.repository.InternsManagementWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class InternsManagementWorkerService {
    @Autowired
    private InternsManagementWorkerRepository internsManagementWorkerRepository;

    static Logger logger = Logger.getLogger(String.valueOf(InternsManagementWorkerService.class));

    public void addAnIntern(Intern intern){
        this.internsManagementWorkerRepository.save(intern);
    }


    @JmsListener(destination = "GetByIdResponseQueue", containerFactory = "GetByIdResponseConsumer")
    public void receiveFromGetRequest(@Payload com.alain.dto.Intern internXml) {
        logger.info("[CONSUMING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() +
                ", First_Name : " + internXml.getFirstName() +
                ", Last_Name : " + internXml.getLastName() +"}");
        //this.updateCache(internXml);
        Intern internEntity = InternMapper.MAPPER.fromXmlToEntityIntern(internXml);
        try {
            this.addAnIntern(internEntity);
            logger.info("[CACHE] ADDED {" +
                    "Intern_ID : " + internXml.getIdIntern() +
                    ", First_Name : " + internXml.getFirstName() +
                    ", Last_Name : " + internXml.getLastName() +"}");        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
