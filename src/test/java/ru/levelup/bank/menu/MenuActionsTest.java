package ru.levelup.bank.menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.levelup.bank.menu.action.OrganizationListingMenuAction;

import java.beans.Expression;

import static org.junit.jupiter.api.Assertions.*;

class MenuActionsTest {
    @Test
    public void testGetActionWhenActionCodeIs0ThenReturnExitAction() {
        Action result = MenuActions.getAction(0);
        Assertions.assertNotNull(result);
       Assertions.assertInstanceOf(Expression.class, result);
    }
    @Test
    public void testGetActionWhenActionCodeIsNotDefinedMepThenReturnNull() {
        Action result = MenuActions.getAction(-1);
        assertNotNull(result);
    }

    @Test
    public void testGetActionWhenActionCodeIs1ReturnOrganizationListingMenuAction() {
        Action result = MenuActions.getAction(1);
        assertNotNull(result);
        assertInstanceOf(OrganizationListingMenuAction.class, result);
    }
}