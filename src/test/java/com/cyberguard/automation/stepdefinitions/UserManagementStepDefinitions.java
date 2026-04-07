package com.cyberguard.automation.stepdefinitions;

import com.cyberguard.automation.questions.ActionButtonsVisibleForUser;
import com.cyberguard.automation.questions.UserInTable;
import com.cyberguard.automation.questions.UserStatusBadge;
import com.cyberguard.automation.tasks.CreateUser;
import com.cyberguard.automation.tasks.ToggleUserStatus;
import com.cyberguard.automation.ui.UserManagementTargets;
import com.cyberguard.automation.util.TestData;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class UserManagementStepDefinitions {

    private String previousStatus;

    @Entonces("la tabla de usuarios es visible con al menos un registro")
    public void userTableIsVisible() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(UserManagementTargets.DATA_TABLE.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Y("crea un nuevo usuario con datos válidos generados dinámicamente")
    public void createUserWithDynamicData() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                CreateUser.withData(
                        TestData.NEW_USER_EMAIL,
                        TestData.NEW_USER_FULLNAME,
                        TestData.NEW_USER_USERNAME,
                        TestData.NEW_USER_ROLE
                )
        );
    }

    @Entonces("se muestra un mensaje de éxito al crear el usuario")
    public void userSuccessMessageIsShown() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.SUCCESS_ALERT, isVisible()).forNoMoreThan(15).seconds()
        );
        assertThat(UserManagementTargets.SUCCESS_ALERT.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Y("el nuevo usuario aparece en la tabla de usuarios")
    public void newUserAppearsInTable() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(actor.asksFor(UserInTable.withUsername(TestData.NEW_USER_USERNAME))).isTrue();
    }

    @Y("intenta crear un usuario con el email {string} ya existente")
    public void tryCreateUserWithDuplicateEmail(String email) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                CreateUser.withData(
                        email,
                        "Duplicate User",
                        "duplicate.user." + System.currentTimeMillis(),
                        "soc_analyst"
                )
        );
    }

    @Entonces("se muestra un mensaje de error en la gestión de usuarios")
    public void userErrorMessageIsShown() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.ERROR_ALERT, isVisible()).forNoMoreThan(15).seconds()
        );
        assertThat(UserManagementTargets.ERROR_ALERT.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Entonces("no se muestran botones de acción para el usuario {string}")
    public void noActionButtonsForUser(String username) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(actor.asksFor(ActionButtonsVisibleForUser.withUsername(username))).isFalse();
    }

    @Y("cambia el estado del usuario {string}")
    public void toggleUserStatus(String username) {
        Actor actor = OnStage.theActorInTheSpotlight();
        previousStatus = actor.asksFor(UserStatusBadge.forUser(username));
        actor.attemptsTo(
                ToggleUserStatus.forUser(username)
        );
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Entonces("el estado del usuario {string} ha cambiado")
    public void userStatusHasChanged(String username) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String newStatus = actor.asksFor(UserStatusBadge.forUser(username));
        assertThat(newStatus).isNotEqualTo(previousStatus);
    }

    @Cuando("cambia nuevamente el estado del usuario {string}")
    public void toggleUserStatusAgain(String username) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                ToggleUserStatus.forUser(username)
        );
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Entonces("el usuario {string} está activo nuevamente")
    public void userIsActiveAgain(String username) {
        Actor actor = OnStage.theActorInTheSpotlight();
        assertThat(actor.asksFor(UserStatusBadge.forUser(username))).isEqualTo("ACTIVO");
    }
}
