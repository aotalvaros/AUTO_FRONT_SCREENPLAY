package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class SidebarTargets {

    public static final Target SIDEBAR =
            Target.the("sidebar de navegación").located(By.cssSelector(".sidebar"));

    public static final Target TOGGLE_BUTTON =
            Target.the("botón toggle del sidebar").located(By.cssSelector(".sidebar__toggle"));

    public static final Target NAV_ITEMS =
            Target.the("ítems de navegación del sidebar").located(By.cssSelector(".sidebar__item"));

    public static final Target NAV_LINK =
            Target.the("enlace de navegación del sidebar").located(By.cssSelector(".sidebar__link"));

    public static final Target ACTIVE_LINK =
            Target.the("enlace activo del sidebar").located(By.cssSelector(".sidebar__link--active"));

    public static final Target NAV_LABEL =
            Target.the("etiqueta de navegación del sidebar").located(By.cssSelector(".nav-label"));
}
