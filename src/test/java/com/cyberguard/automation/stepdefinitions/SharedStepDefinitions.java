package com.cyberguard.automation.stepdefinitions;

import com.cyberguard.automation.tasks.Authenticate;
import com.cyberguard.automation.tasks.NavigateTo;
import com.cyberguard.automation.ui.CyberGuardLoginPage;
import com.cyberguard.automation.util.TestData;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;

public class SharedStepDefinitions {

    @Dado("que el administrador ha iniciado sesión en CyberGuard")
    public void adminLogsIn() {
        Actor admin = OnStage.theActorCalled("admin");
        admin.attemptsTo(
                Open.browserOn().the(CyberGuardLoginPage.class),
                Authenticate.withCredentials(TestData.ADMIN_EMAIL, TestData.ADMIN_PASSWORD)
        );
    }

    @Dado("que el manejador de incidentes ha iniciado sesión en CyberGuard")
    public void handlerLogsIn() {
        Actor handler = OnStage.theActorCalled("handler");
        handler.attemptsTo(
                Open.browserOn().the(CyberGuardLoginPage.class),
                Authenticate.withCredentials(TestData.HANDLER_EMAIL, TestData.HANDLER_PASSWORD)
        );
    }

    @Cuando("navega a la página de incidentes")
    public void navigateToIncidents() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavigateTo.theIncidentsPage()
        );
    }

    @Cuando("navega a la página de gestión de usuarios")
    public void navigateToUsers() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavigateTo.theUsersPage()
        );
    }
}
