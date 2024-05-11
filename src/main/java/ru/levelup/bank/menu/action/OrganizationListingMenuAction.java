package ru.levelup.bank.menu.action;

import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.MenuAction;

@MenuAction(actionCode = 1)
public class OrganizationListingMenuAction implements Action {

    @Override
    public void execute() {
        System.out.println("organization listing");
    }
}
