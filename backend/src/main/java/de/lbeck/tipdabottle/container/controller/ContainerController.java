package de.lbeck.tipdabottle.container.controller;

import de.lbeck.tipdabottle.container.dto.ContainerDTO;
import de.lbeck.tipdabottle.container.service.ContainerService;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/containers")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping
    public List<ContainerDTO> getAllContainers() {
        return containerService.getAllContainers();
    }
}
