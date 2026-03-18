package com.cyberguard.automation.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginForm {

    public static final Target USERNAME_FIELD =
            Target.the("campo de usuario").located(By.cssSelector("#username"));

    public static final Target PASSWORD_FIELD =
            Target.the("campo de contraseña").located(By.cssSelector("#password"));

    public static final Target LOGIN_BUTTON =
            Target.the("botón de inicio de sesión").located(By.cssSelector("button[type='submit']"));
}
