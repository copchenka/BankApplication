package ru.levelup.bank.menu.action;

import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.ConsoleMenu;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.hbm.HibernateOrganizationRepository;

@MenuAction(actionCode = 5)

public class CreateOrganizationMenuAction implements Action {
    private final HibernateOrganizationRepository organizationRepository;

    public CreateOrganizationMenuAction() {
        this.organizationRepository = new HibernateOrganizationRepository(HibernateConfiguration.getFactory());
    }

    @Override
    public void execute() {
        String vatin = ConsoleMenu.readString("Введите ИНН");
        if (organizationRepository.byVatin(vatin) != null) {
            System.out.println("Организации с таким ИНН уже существует");
        } else {
            String name = ConsoleMenu.readString("Введите название организации");
            Organization organization = organizationRepository.create(null, name, vatin);
            if (organization != null) System.out.println("Организация успешно создана");
        }
    }
}
