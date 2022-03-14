package com.alain.service;


import com.alain.dto.json.Intern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Component
@Service
public class InternsManagementHttpApiService {

    @Autowired
    private JmsProducer jmsProducer;

    public List<Intern> getAllInterns() {
        return null;
    }

    public Intern getAnInternByFirstName(String firstName){
        return null;
    }

    public void addAnIntern(Intern internJson) {
        this.jmsProducer.sendMessage(internJson);
    }

    public void deleteAnIntern(Long idIntern) {
    }

    public void updateAnIntern(Intern intern) {
    }
}
