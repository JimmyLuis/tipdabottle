package de.lbeck.tipdabottle.container.controller;

import de.lbeck.tipdabottle.common.annotations.View;
import de.lbeck.tipdabottle.container.dto.out.ResponseContainerPublicDTO;
import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.container.service.ContainerService;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @View(Container.class)
    @GetMapping
    public List<Container> getAllContainers() {
        return containerService.getAllContainers();
    }
}
