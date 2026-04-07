package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class IncidentTargets {

    public static final Target PAGE_TITLE =
            Target.the("título de página de incidentes").located(By.cssSelector(".page-header h1"));

    public static final Target CREATE_BUTTON =
            Target.the("botón crear incidente").located(By.cssSelector(".toolbar .btn-primary"));

    public static final Target FORM_CARD =
            Target.the("formulario de incidente").located(By.cssSelector(".form-card"));

    public static final Target THREAT_ID_INPUT =
            Target.the("campo threatId").located(By.cssSelector("input[formControlName='threatId']"));

    public static final Target SUBMIT_BUTTON =
            Target.the("botón submit del formulario de incidente").located(By.cssSelector(".form-actions .btn-primary"));

    public static final Target DATA_TABLE =
            Target.the("tabla de incidentes").located(By.cssSelector(".data-table"));

    public static final Target TABLE_ROWS =
            Target.the("filas de la tabla de incidentes").located(By.cssSelector(".data-table tbody tr"));

    public static final Target TABLE_CONTAINER =
            Target.the("contenedor de tabla de incidentes").located(By.cssSelector(".table-container"));

    public static final Target FILTERS_BAR =
            Target.the("barra de filtros de incidentes").located(By.cssSelector(".filters-bar"));

    public static final Target SUCCESS_ALERT =
            Target.the("alerta de éxito en incidentes").located(By.cssSelector(".alert.alert-success"));

    public static final Target ERROR_ALERT =
            Target.the("alerta de error en incidentes").located(By.cssSelector(".alert.alert-error"));
}
