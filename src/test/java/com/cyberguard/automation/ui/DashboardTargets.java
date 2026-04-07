package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DashboardTargets {

    public static final Target CONTAINER =
            Target.the("contenedor del dashboard").located(By.cssSelector(".dashboard-container"));

    public static final Target TITLE =
            Target.the("título del dashboard").located(By.cssSelector(".dashboard-header h1"));

    public static final Target MAIN =
            Target.the("área principal del dashboard").located(By.cssSelector(".dashboard-main"));
}
