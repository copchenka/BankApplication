package ru.levelup.bank.menu;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import ru.levelup.bank.menu.action.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MenuActions {
    private static final Map<Integer, Action> ACTION_MAP = new HashMap<>();
//
//    static { //лучше  чем if,switch
//        ACTION_MAP.put(1, new OrganizationListingMenuAction());
//        ACTION_MAP.put(0, new ExitAction());
//        ACTION_MAP.put(2, new CustomerListMenuAction());
//        ACTION_MAP.put(3, new CustomerDetailsMenuAction());
//        ACTION_MAP.put(4, new AssignCustomerToOrganizationMenuAction());
//
//    }

    public static void initializeMenu() {
        //1.найти все классы у которых есть аннотация @MenuAction
        //2.найти все классы которые реализовали интерфейс Action

        //2var
        try {
            Reflections reflections = new Reflections("ru.levelup.bank", Scanners.SubTypes);
            Set<Class<? extends Action>> actionClasses = reflections.getSubTypesOf(Action.class);
            actionClasses.forEach(it -> System.out.println(it.getName()));

            for (Class<? extends Action> actionClass : actionClasses) {
                MenuAction annotation = actionClass.getAnnotation(MenuAction.class);
                if (annotation != null) {
                    int actionCode = annotation.actionCode();
                    Constructor<? extends Action> defaultConstuctor = actionClass.getDeclaredConstructor();
                    defaultConstuctor.setAccessible(true);

                    Action action = actionClass.getDeclaredConstructor().newInstance();
                    ACTION_MAP.put(actionCode, action);
                } else {
                    System.out.println("WARN: Class" + actionClass.getName() + " doesn't have annotation");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Action getAction(int actionCode) {
        return ACTION_MAP.get(actionCode);
    }
}
