package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.UserManagementTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CreateUser implements Task {

    private final String email;
    private final String fullName;
    private final String username;
    private final String role;

    public CreateUser(String email, String fullName, String username, String role) {
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
    }

    public static CreateUser withData(String email, String fullName, String username, String role) {
        return instrumented(CreateUser.class, email, fullName, username, role);
    }

    @Override
    @Step("{0} crea un usuario con email #email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.NEW_USER_BUTTON, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(UserManagementTargets.NEW_USER_BUTTON),
                WaitUntil.the(UserManagementTargets.FORM_CARD, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(email).into(UserManagementTargets.EMAIL_INPUT),
                Enter.theValue(fullName).into(UserManagementTargets.FULLNAME_INPUT),
                Enter.theValue(username).into(UserManagementTargets.USERNAME_INPUT),
                SelectFromOptions.byValue(role).from(UserManagementTargets.ROLE_SELECT),
                Click.on(UserManagementTargets.SUBMIT_BUTTON)
        );
    }
}
