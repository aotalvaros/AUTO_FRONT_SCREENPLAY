package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ThreatReportForm {

    public static final Target TYPE_SELECT =
            Target.the("tipo de amenaza").located(By.cssSelector("select#type"));

    public static final Target SEVERITY_SELECT =
            Target.the("severidad de amenaza").located(By.cssSelector("select#severity"));

    public static final Target SOURCE_IP_FIELD =
            Target.the("IP de origen").located(By.cssSelector("input#sourceIp"));

    public static final Target DESCRIPTION_FIELD =
            Target.the("descripción de amenaza").located(By.cssSelector("textarea#description"));

    public static final Target SUBMIT_BUTTON =
            Target.the("botón reportar amenaza").located(By.cssSelector(".threat-form-card button[type='submit']"));

    public static final Target SUCCESS_MESSAGE =
            Target.the("mensaje de éxito").located(By.cssSelector("div.alert-success"));

    public static final Target VALIDATION_ERRORS =
            Target.the("mensajes de validación").located(By.cssSelector(".threat-form-card span.error-message"));
}
