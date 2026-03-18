package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.LoginForm;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Authenticate implements Task {

    private final String username;
    private final String password;

    public Authenticate(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Authenticate withCredentials(String username, String password) {
        return instrumented(Authenticate.class, username, password);
    }

    @Override
    @Step("{0} inicia sesión con el usuario #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginForm.USERNAME_FIELD, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(username).into(LoginForm.USERNAME_FIELD),
                Enter.theValue(password).into(LoginForm.PASSWORD_FIELD),
                Click.on(LoginForm.LOGIN_BUTTON)
        );
    }
}
