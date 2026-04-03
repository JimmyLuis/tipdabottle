package de.lbeck.tipdabottle.container.service;

import de.lbeck.tipdabottle.container.dto.ContainerDTO;
import de.lbeck.tipdabottle.container.dto.ContainerMapper;
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

    public List<ContainerDTO> getAllContainers() {
        List<ContainerDTO> customerDTOList = new ArrayList<>();
        containerRepository.findAll().forEach(container -> {
            customerDTOList.add(mapper.toDTO(container));
        });
        return customerDTOList;
    }
}