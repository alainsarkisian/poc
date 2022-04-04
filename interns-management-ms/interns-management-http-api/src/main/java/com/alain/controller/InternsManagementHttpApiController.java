package com.alain.controller;

import com.alain.dto.controller.InternsManagementHttpApiInterface;
import com.alain.dto.json.Intern;
import com.alain.service.InternsManagementHttpApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/interns-management-ms/")
@RestController
@RequiredArgsConstructor //Thanks to lombok, no need to define manually a constructor with args
/*
    No need to use @Override on implemented methods because in the interface they are empty,
    There are nothing to over ride
 */
public class InternsManagementHttpApiController implements InternsManagementHttpApiInterface {
    /*
        In order to avoid @Autowired, we can define a final attribute and a constructor
        with args (@RequiredArgsConstructor) thanks to lombok
     */
    private final InternsManagementHttpApiService internsManagementHttpApiService;

    /*
        Currently our app is asynchronous client designed because of the use of Queues
        What we want to do here is to simulate a synchronous call in the case that the client needs a direct and real
        time response for its request.
        If the client wants to see a specific intern it has not to wait 1 hour until a worker send back the intern
        by the queue.
        So the goal is to directly insert the response into a redis cache, thus, the http-api, just after sending the
        request will block on the cache by reading it during a certain time (while condition) simulating the synchronous
        request. After requesting for a specific intern, the client will not be able to send another request while the
        get request answer is not arrived.
     */
    ///@GetMapping("interns/{id}")
    public Intern getAnInternById(@PathVariable Long id) {
        return this.internsManagementHttpApiService.getAnInternByIdInCacheOrDb(id);
    }

    /*
        We return directly a response after the post request of the client because we suppose that
        what happens next is not interesting for the client.
        We are just telling the client its request has been taken in account and the intern will be added but not when
        We do not need synchronous client here.
     */
    ///@PostMapping("interns")
    public String addAnIntern(@RequestBody Intern intern) {
        this.internsManagementHttpApiService.addAnIntern(intern);
        return "Your resquest has been taken in account";
    }

    //@DeleteMapping("interns/{idIntern}")
    public void deleteAnIntern(@PathVariable Long idIntern) {
        this.internsManagementHttpApiService.deleteAnIntern(idIntern);
    }

    //@PutMapping("interns")
    public void updateAnIntern(@RequestBody Intern intern){
        this.internsManagementHttpApiService.updateAnIntern(intern);
    }

    public Intern getAnInternByFirstNameUsingRequestParam(@RequestParam String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

        /*
    @GetMapping("interns/{firstName}")
    public Intern getAnInternByFirstName(@PathVariable String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }
    */

        /*
    public List<Intern> getAllInterns() {
        return this.internsManagementHttpApiService.getAllInterns();
    }*/

}
