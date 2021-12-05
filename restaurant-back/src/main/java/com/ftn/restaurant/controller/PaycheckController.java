package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.UpdatePaycheckDTO;
import com.ftn.restaurant.dto.UserPaycheckDTO;
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
    public UserPaycheckDTO changePaycheck(@AuthenticationPrincipal User user, @RequestBody UpdatePaycheckDTO updatePaycheckDTO) {
        LOG.info("Updating paycheck...");
        return new UserPaycheckDTO(paycheckService.updatePaycheck(updatePaycheckDTO), updatePaycheckDTO.getNewSalary());
    }

}
