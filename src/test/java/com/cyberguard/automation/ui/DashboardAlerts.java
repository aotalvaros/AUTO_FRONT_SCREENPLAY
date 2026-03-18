package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DashboardAlerts {

    public static final Target ALERT_CARDS =
            Target.the("tarjetas de alerta").located(By.cssSelector("div.alert-card"));

    public static final Target ALERT_DESCRIPTIONS =
            Target.the("descripciones de alertas").located(By.cssSelector("p.alert-description"));
}
