package com.cyberguard.automation.stepdefinitions;

import com.cyberguard.automation.questions.AlertsVisible;
import com.cyberguard.automation.questions.SuccessMessage;
import com.cyberguard.automation.questions.ValidationMessages;
import com.cyberguard.automation.tasks.Authenticate;
import com.cyberguard.automation.tasks.NavigateTo;
import com.cyberguard.automation.tasks.ReportThreat;
import com.cyberguard.automation.tasks.SubmitEmptyThreatForm;
import com.cyberguard.automation.ui.CyberGuardLoginPage;
import com.cyberguard.automation.ui.DashboardAlerts;
import com.cyberguard.automation.ui.ThreatReportForm;
import com.cyberguard.automation.util.TestData;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistroAmenazaStepDefinitions {

    @Dado("que el analista ha iniciado sesión en CyberGuard System")
    public void analystLogsIn() {
        Actor analista = OnStage.theActorCalled("analista");
        analista.attemptsTo(
                Open.browserOn().the(CyberGuardLoginPage.class),
                Authenticate.withCredentials(TestData.ADMIN_USERNAME, TestData.ADMIN_PASSWORD),
                NavigateTo.theReportThreatPage()
        );
    }

    @Cuando("reporta una amenaza de tipo {string} con severidad {string} y descripción {string}")
    public void reportThreat(String type, String severity, String description) {
        Actor analista = OnStage.theActorInTheSpotlight();
        analista.attemptsTo(
                ReportThreat.withData(
                        mapType(type),
                        mapSeverity(severity),
                        TestData.VALID_SOURCE_IP,
                        description
                )
        );
    }

    @Cuando("intenta reportar una amenaza sin completar los campos obligatorios")
    public void submitEmptyForm() {
        Actor analista = OnStage.theActorInTheSpotlight();
        analista.attemptsTo(SubmitEmptyThreatForm.attempt());
    }

    @Entonces("el sistema confirma que la amenaza fue registrada exitosamente")
    public void confirmThreatReported() {
        Actor analista = OnStage.theActorInTheSpotlight();
        // WaitUntil already throws AssertionError if element never becomes visible;
        // the extra asksFor() check is omitted to avoid a race with the 2-second
        // navigate(['/dashboard']) redirect that fires after success.
        analista.attemptsTo(
                WaitUntil.the(ThreatReportForm.SUCCESS_MESSAGE, isVisible()).forNoMoreThan(15).seconds()
        );
    }

    @Entonces("el sistema muestra mensajes de validación indicando los campos requeridos")
    public void validationMessagesAreShown() {
        Actor analista = OnStage.theActorInTheSpotlight();
        assertThat(analista.asksFor(ValidationMessages.areDisplayed())).isTrue();
    }

    @Y("el mensaje de confirmación incluye el identificador de la amenaza")
    public void confirmationIncludesThreatId() {
        Actor analista = OnStage.theActorInTheSpotlight();
        String successText = ThreatReportForm.SUCCESS_MESSAGE.resolveFor(analista).getText();
        assertThat(successText).containsIgnoringCase("ID");
    }

    @Y("el reporte no es enviado al sistema")
    public void reportNotSubmitted() {
        Actor analista = OnStage.theActorInTheSpotlight();
        assertThat(analista.asksFor(SuccessMessage.isDisplayed())).isFalse();
    }

    private String mapType(String displayType) {
        return displayType.toLowerCase();
    }

    private String mapSeverity(String displaySeverity) {
        return switch (displaySeverity.toLowerCase()) {
            case "alta" -> "high";
            case "media" -> "medium";
            case "baja" -> "low";
            case "crítica" -> "critical";
            default -> displaySeverity.toLowerCase();
        };
    }
}
