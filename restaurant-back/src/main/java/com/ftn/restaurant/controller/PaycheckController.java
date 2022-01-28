package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.UpdatePaycheckDTO;
import com.ftn.restaurant.dto.UserPaycheckDTO;
import com.ftn.restaurant.model.Manager;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.PaycheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api/paychecks")
public class PaycheckController {

    private static final Logger LOG = LoggerFactory.getLogger(PaycheckController.class);

    private final PaycheckService paycheckService;

    @Autowired
    public PaycheckController(PaycheckService paycheckService) {
        this.paycheckService = paycheckService;
    }

    @ResponseBody
    @PostMapping(path = "/changePaycheck")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public UserPaycheckDTO changePaycheck(@RequestBody UpdatePaycheckDTO updatePaycheckDTO) {
        LOG.info("Updating paycheck...");
        return new UserPaycheckDTO(paycheckService.updatePaycheck(updatePaycheckDTO).getEmployee(), updatePaycheckDTO.getNewSalary());
    }

    @ResponseBody
    @GetMapping(path = "/getCurrent")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserPaycheckDTO> getCurrentPaychecks(@RequestParam(name= "username") String username, @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName,
                                                     @RequestParam(name = "filterName", required = false, defaultValue = "") String filterName) {
        LOG.info("Getting active paychecks...");
        List<UserPaycheckDTO> userPaycheckDTOS = new ArrayList<>();
        paycheckService.getCurrentPaychecks(username, searchName, filterName).forEach(paycheck -> userPaycheckDTOS.add(new UserPaycheckDTO(paycheck)));
        return userPaycheckDTOS;
    }

}
