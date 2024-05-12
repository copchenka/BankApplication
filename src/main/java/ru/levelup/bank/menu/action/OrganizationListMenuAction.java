package ru.levelup.bank.menu.action;

import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.OrganizationRepository;
import ru.levelup.bank.repository.hbm.HibernateOrganizationRepository;

import java.util.List;

@MenuAction(actionCode = 1)
public class OrganizationListMenuAction implements Action {

    private OrganizationRepository organizationRepository;

    public OrganizationListMenuAction() {
        this.organizationRepository = new HibernateOrganizationRepository(HibernateConfiguration.getFactory());
    }

    @Override
    public void execute() {
        List<Organization> organizations = organizationRepository.all();
        if (organizations==null) System.out.println("Организаций нет");
        else{
            System.out.println("Список организаций: ");
            organizations.forEach(organization -> System.out.println(organization));
        }
    }
}
