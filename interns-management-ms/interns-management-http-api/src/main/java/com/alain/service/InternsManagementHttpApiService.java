package com.alain.service;


import com.alain.dto.json.Intern;
import com.alain.dto.mapper.InternMapper;
import com.alain.singleton.CacheSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Component
@Service
public class InternsManagementHttpApiService {

    @Autowired
    private JmsProducer jmsProducer;

    @Autowired
    private CacheSingleton cacheSingleton;

    static Logger logger = Logger.getLogger(String.valueOf(InternsManagementHttpApiService.class));

    /*
        Add response from Get to the cache
    */
    public synchronized boolean updateCache(com.alain.dto.Intern internXml){
        boolean isUpdated = false;
        try {
            this.cacheSingleton.getCache().put(internXml.getIdIntern(), internXml);
            logger.info("[CACHE] ADDED " +
                    "Intern_ID : " + internXml.getIdIntern() +
                    ", First_Name : " + internXml.getFirstName() +
                    ", Last_Name : " + internXml.getLastName() +"}");
            isUpdated = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
            return isUpdated;
    }

    /*
        Find the cache value for a specific input key
     */
    public synchronized com.alain.dto.Intern getCacheValue(BigInteger key){
        try{
            com.alain.dto.Intern intern = this.cacheSingleton.getCache().get(key);
            logger.info("[CACHE] READING " +
                    "Intern_ID : " + intern.getIdIntern() +
                    ", First_Name : " + intern.getFirstName() +
                    ", Last_Name : " + intern.getLastName() +"}");
            return intern;
        }
        catch (NullPointerException e){
            com.alain.dto.Intern intern = new com.alain.dto.Intern();
            intern.setIdIntern(BigInteger.valueOf(0));
            return intern;
        }
    }

    /*
    Consume from Get Response Queue
    */
    @JmsListener(destination = "GetByIdResponseQueue", containerFactory = "GetByIdResponseConsumer")
    public void receiveFromGetRequest(@Payload com.alain.dto.Intern internXml) {
        logger.info("[CONSUMING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() +
                ", First_Name : " + internXml.getFirstName() +
                ", Last_Name : " + internXml.getLastName() +"}");
        this.updateCache(internXml);
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
    public Intern getAnInternById(Long id) throws InterruptedException {
        com.alain.dto.Intern internXml = new com.alain.dto.Intern();
        com.alain.dto.Intern cacheResult = new com.alain.dto.Intern();

        internXml.setIdIntern(BigInteger.valueOf(id));
        this.jmsProducer.sendMessageForGetById(internXml);

        long currentTime = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime);

        while(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timeSeconds < 5  &&
        this.getCacheValue(BigInteger.valueOf(id)).getIdIntern() == BigInteger.valueOf(0)) {
            this.getCacheValue(BigInteger.valueOf(id));
        }
        cacheResult =  this.getCacheValue(BigInteger.valueOf(id));

        if(cacheResult.getIdIntern() == BigInteger.valueOf(0)) {
            logger.info("[TIME OUT] OCCURRED");
        }
        Intern finalResult = InternMapper.MAPPER.fromXmlToJsonIntern(cacheResult);
        return finalResult;
    }

    /*
        Check first if the cache got the intern that the client is asking for
        If not, send the id to the get request queue
     */
    public Intern getAnInternByIdInCacheOrDb(Long id) throws InterruptedException {
        com.alain.dto.Intern isInternPresentInCache = this.getCacheValue(BigInteger.valueOf(id));
        if(isInternPresentInCache.getIdIntern() != BigInteger.valueOf(0)){
            System.out.println(isInternPresentInCache.getIdIntern());
            System.out.println(BigInteger.valueOf(0));
            logger.info("[CACHE] INTERN ALREADY IN CACHE");
            Intern finalResult = InternMapper.MAPPER.fromXmlToJsonIntern(isInternPresentInCache);
            return finalResult;
        }
        else {
            logger.info("[CACHE] INTERN NOT IN CACHE");
            return this.getAnInternById(id);
        }
    }

    public void deleteAnIntern(Long idIntern) {}

    public void updateAnIntern(Intern intern) {
    }

    public List<Intern> getAllInterns() {return null;}

    public Intern getAnInternByFirstName(String firstName){return null;}
}