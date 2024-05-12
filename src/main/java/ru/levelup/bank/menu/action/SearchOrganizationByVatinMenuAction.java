package ru.levelup.bank.menu.action;

import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.OrganizationRepository;
import ru.levelup.bank.repository.hbm.HibernateOrganizationRepository;
import ru.levelup.bank.menu.ConsoleMenu;

@MenuAction(actionCode = 6)
public class SearchOrganizationByVatinMenuAction implements Action {
    private OrganizationRepository organizationRepository;

    public SearchOrganizationByVatinMenuAction() {
        this.organizationRepository = new HibernateOrganizationRepository(HibernateConfiguration.getFactory());
    }

    @Override
    public void execute() {
        String vatin = ConsoleMenu.readString("Введите ИНН");
        Organization organization = organizationRepository.byVatin(vatin);
        if (organization != null) {
            System.out.println("Организация с ИНН " + vatin + ":");
            System.out.println(organization);
        } else {
            System.out.println("Организации с таким ИНН не найдено");
        }
    }
}
