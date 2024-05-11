package ru.levelup.bank.menu.action;

import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.MenuAction;

@MenuAction(actionCode = 0)
public class ExitAction implements Action {
    @Override
    public void execute() {
        System.out.println("Пока!)");
    }
}
