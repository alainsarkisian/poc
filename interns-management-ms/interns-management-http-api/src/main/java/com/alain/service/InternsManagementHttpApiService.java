package com.alain.service;


import com.alain.dto.json.Intern;
import com.alain.mapper.InternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Component
@Service
public class InternsManagementHttpApiService {

    @Autowired
    private JmsProducer jmsProducer;

    @Autowired
    private RedisService redisService;
    //private CacheSingleton cacheSingleton;

    static Logger logger = Logger.getLogger(String.valueOf(InternsManagementHttpApiService.class));

    /*
        Add response from Get to the cache

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
     */

    /*
        Find the cache value for a specific input key
     */
    public synchronized com.alain.model.Intern getCacheValue(Long key){
        try{
            Optional<com.alain.model.Intern> intern = this.redisService.getAnInternById(key);
            logger.info("[CACHE] READING " +
                    "Intern_ID : " + intern.get().getIdIntern()+
                    ", First_Name : " + intern.get().getFirstName() +
                    ", Last_Name : " + intern.get().getLastName() +"}");
            if(intern.isPresent()){
                return intern.get();
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }

    /*
    Find the cache value for a specific input key
 */
    public synchronized com.alain.model.Intern getCacheValueWithoutLog(Long key){
        try{
            Optional<com.alain.model.Intern> intern = this.redisService.getAnInternById(key);
            if(intern.isPresent()){
                return intern.get();
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }

    /*
    Consume from Get Response Queue

    @JmsListener(destination = "GetByIdResponseQueue", containerFactory = "GetByIdResponseConsumer")
    public void receiveFromGetRequest(@Payload com.alain.dto.Intern internXml) {
        logger.info("[CONSUMING : GET] {" +
                "Intern_ID : " + internXml.getIdIntern() +
                ", First_Name : " + internXml.getFirstName() +
                ", Last_Name : " + internXml.getLastName() +"}");
        this.updateCache(internXml);
    }

     */

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
        /*
        Prepare to send the InternXml into Get Request Queue
         */
        com.alain.dto.Intern internXml = new com.alain.dto.Intern();
        internXml.setIdIntern(BigInteger.valueOf(id));
        ///Send it
        this.jmsProducer.sendMessageForGetById(internXml);


        long currentTime = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime);

        while(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timeSeconds < 5  &&
        this.getCacheValueWithoutLog(id) == null) {
            this.getCacheValueWithoutLog(id);
        }
        com.alain.model.Intern cacheResult = this.getCacheValue(id);

        if(cacheResult == null) {
            logger.info("[TIME OUT] OCCURRED INTERN DOES NOT EXIST");
            return null;
        }
        Intern finalResult = InternMapper.MAPPER.fromEntityToJson(cacheResult);
        return finalResult;
    }

    /*
        Check first if the cache got the intern that the client is asking for
        If not, send the id to the get request queue
     */
    public Intern getAnInternByIdInCacheOrDb(Long id){
        com.alain.model.Intern isInternPresentInCache = this.getCacheValueWithoutLog(id);
        if(isInternPresentInCache != null){
            logger.info("[CACHE] INTERN ALREADY IN CACHE");
            Intern finalResult = InternMapper.MAPPER.fromEntityToJson(isInternPresentInCache);
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