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

    @GetMapping("interns")
    public List<Intern> getAllInterns() {
        return this.internsManagementHttpApiService.getAllInterns();
    }

    @GetMapping("intern/{firstName}")
    public Intern getAnInternByFirstName(@PathVariable String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @GetMapping("intern")
    public Intern getAnInternByFirstNameUsingRequestParam(@RequestParam String firstName){
        return this.internsManagementHttpApiService.getAnInternByFirstName(firstName);
    }

    @PostMapping("intern")
    public String addAnIntern(@RequestBody Intern intern) {
        this.internsManagementHttpApiService.addAnIntern(intern);
        return "Your resquest has been taken in account";
    }

    @DeleteMapping("intern/{idIntern}")
    public void deleteAnIntern(@PathVariable Long idIntern) {
        this.internsManagementHttpApiService.deleteAnIntern(idIntern);
    }

    @PutMapping("intern")
    public void updateAnIntern(@RequestBody Intern intern){
        this.internsManagementHttpApiService.updateAnIntern(intern);
    }
}
