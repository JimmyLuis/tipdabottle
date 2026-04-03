package de.lbeck.tipdabottle.container.service;

import de.lbeck.tipdabottle.container.dto.out.ResponseContainerPublicDTO;
import de.lbeck.tipdabottle.container.dto.ContainerMapper;
import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.container.repository.ContainerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContainerService {

    private final ContainerRepository containerRepository;
    private final ContainerMapper mapper;
    public ContainerService(ContainerRepository containerRepository, ContainerMapper mapper) {
        this.containerRepository = containerRepository;
        this.mapper = mapper;
    }

    public List<Container> getAllContainers() {
        List<Container> customerList = new ArrayList<>();
        containerRepository.findAll().forEach(customerList::add);
        return customerList;
    }
}