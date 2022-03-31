package com.alain.dto.controller;

import com.alain.dto.json.Intern;
import org.springframework.web.bind.annotation.*;
/*
    Here is the interface that exposes endpoints to the client/front-end
    The goal is to only expose a module that contains an interface and json dto
    Which are both the unique elements that the clients has to know and need to know in order to send requests
    No need to also "give access" to an interface which is in a module that contains also packages like
    JmsConfig or even SecurityConfig
 */
@RequestMapping("api/v1/interns-management-ms/")
public interface InternsManagementHttpApiInterface {

    @GetMapping("interns/{id}")
    Intern getAnInternById(@PathVariable Long id) throws InterruptedException;

    @PostMapping("interns")
    String addAnIntern(@RequestBody Intern intern);

    /*
    @GetMapping("interns/{firstName}")
    public Intern getAnInternByFirstName(@PathVariable String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @GetMapping("interns")
    public Intern getAnInternByFirstNameUsingRequestParam(@RequestParam String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @GetMapping("internss")
    public List<Intern> getAllInterns();

    @DeleteMapping("interns/{idIntern}")
    public void deleteAnIntern(@PathVariable Long idIntern) {
        this.internsManagementHttpApiService.deleteAnIntern(idIntern);
    }

    @PutMapping("interns")
    public void updateAnIntern(@RequestBody Intern intern){
        this.internsManagementHttpApiService.updateAnIntern(intern);
    }
    */
}