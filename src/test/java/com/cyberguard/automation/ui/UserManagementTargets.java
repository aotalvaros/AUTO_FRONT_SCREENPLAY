package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UserManagementTargets {

    public static final Target PAGE_TITLE =
            Target.the("título de la página de gestión de usuarios").located(By.cssSelector(".page-header h1"));

    public static final Target NEW_USER_BUTTON =
            Target.the("botón nuevo usuario").located(By.cssSelector(".toolbar .btn-primary"));

    public static final Target FORM_CARD =
            Target.the("formulario de nuevo usuario").located(By.cssSelector(".form-card"));

    public static final Target EMAIL_INPUT =
            Target.the("campo email").located(By.cssSelector("input[formControlName='email']"));

    public static final Target FULLNAME_INPUT =
            Target.the("campo nombre completo").located(By.cssSelector("input[formControlName='fullName']"));

    public static final Target USERNAME_INPUT =
            Target.the("campo username").located(By.cssSelector("input[formControlName='username']"));

    public static final Target ROLE_SELECT =
            Target.the("selector de rol").located(By.cssSelector("select[formControlName='role']"));

    public static final Target SUBMIT_BUTTON =
            Target.the("botón submit del formulario de usuario").located(By.cssSelector(".form-actions .btn-primary"));

    public static final Target DATA_TABLE =
            Target.the("tabla de usuarios").located(By.cssSelector(".data-table"));

    public static final Target TABLE_CONTAINER =
            Target.the("contenedor de tabla de usuarios").located(By.cssSelector(".table-container"));

    public static final Target USER_ROWS =
            Target.the("filas de la tabla de usuarios").located(By.cssSelector(".data-table tbody tr"));

    public static final Target SUCCESS_ALERT =
            Target.the("alerta de éxito en gestión de usuarios").located(By.cssSelector(".alert.alert-success"));

    public static final Target ERROR_ALERT =
            Target.the("alerta de error en gestión de usuarios").located(By.cssSelector(".alert.alert-error"));

    public static final Target ACTIVE_BADGE =
            Target.the("badge estado activo").located(By.cssSelector(".badge.badge-active"));

    public static final Target INACTIVE_BADGE =
            Target.the("badge estado inactivo").located(By.cssSelector(".badge.badge-inactive"));

    public static final Target TU_CUENTA_BADGE =
            Target.the("badge tu cuenta").located(By.cssSelector(".badge.badge-info"));

    public static final Target EDIT_BUTTON =
            Target.the("botón editar usuario").located(By.cssSelector(".btn-sm.btn-edit"));

    public static final Target DEACTIVATE_BTN =
            Target.the("botón desactivar usuario").located(By.cssSelector(".btn-sm.btn-deactivate"));

    public static final Target ACTIVATE_BTN =
            Target.the("botón activar usuario").located(By.cssSelector(".btn-sm.btn-activate"));
}
