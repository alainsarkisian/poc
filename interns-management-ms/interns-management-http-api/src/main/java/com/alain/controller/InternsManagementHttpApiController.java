package com.alain.controller;

import com.alain.dto.json.Intern;
import com.alain.service.InternsManagementHttpApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("api/v1/interns-management-ms/")
@RestController
public class InternsManagementHttpApiController {

    @Autowired
    private InternsManagementHttpApiService internsManagementHttpApiService;

    @GetMapping("internss")
    public List<Intern> getAllInterns() {
        return this.internsManagementHttpApiService.getAllInterns();
    }

    @GetMapping("interns/{id}")
    public Intern getAnInternById(@PathVariable Long id) throws InterruptedException {
        return this.internsManagementHttpApiService.getAnInternById(id);
    }

    /*
    @GetMapping("interns/{firstName}")
    public Intern getAnInternByFirstName(@PathVariable String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }
    */

    @GetMapping("interns")
    public Intern getAnInternByFirstNameUsingRequestParam(@RequestParam String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @PostMapping("interns")
    public String addAnIntern(@RequestBody Intern intern) {
        this.internsManagementHttpApiService.addAnIntern(intern);
        return "Your resquest has been taken in account";
    }

    @DeleteMapping("interns/{idIntern}")
    public void deleteAnIntern(@PathVariable Long idIntern) {
        this.internsManagementHttpApiService.deleteAnIntern(idIntern);
    }

    @PutMapping("interns")
    public void updateAnIntern(@RequestBody Intern intern){
        this.internsManagementHttpApiService.updateAnIntern(intern);
    }
}
