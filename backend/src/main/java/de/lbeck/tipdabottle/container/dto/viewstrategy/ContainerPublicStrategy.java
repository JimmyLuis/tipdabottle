package de.lbeck.tipdabottle.container.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.container.dto.ContainerMapper;
import de.lbeck.tipdabottle.container.model.Container;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ContainerPublicStrategy implements ViewStrategy<Container> {

    private final ContainerMapper containerMapper;

    public ContainerPublicStrategy(ContainerMapper containerMapper) {
        this.containerMapper = containerMapper;
    }

    @Override
    public boolean supports(Authentication auth, Container entity) {
        return true;
    }

    @Override
    public Object map(Container entity) {
        return containerMapper.toPublicDTO(entity);
    }

    @Override
    public Class<Container> getType() {
        return Container.class;
    }
}
