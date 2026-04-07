package com.cyberguard.automation.stepdefinitions;

import com.cyberguard.automation.questions.CreateButtonVisible;
import com.cyberguard.automation.questions.IncidentTableHasRecords;
import com.cyberguard.automation.tasks.CreateIncidentFromThreat;
import com.cyberguard.automation.ui.IncidentTargets;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class IncidentStepDefinitions {

    @Entonces("el botón de crear incidente es visible en la página")
    public void createButtonIsVisible() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.CREATE_BUTTON, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(actor.asksFor(CreateButtonVisible.onIncidentsPage())).isTrue();
    }

    @Entonces("el botón de crear incidente no es visible en la página")
    public void createButtonIsNotVisible() {
        Actor actor = OnStage.theActorInTheSpotlight();
        assertThat(actor.asksFor(CreateButtonVisible.onIncidentsPage())).isFalse();
    }

    @Y("crea un incidente a partir de la amenaza crítica registrada")
    public void createIncidentFromCriticalThreat() throws Exception {
        Actor actor = OnStage.theActorInTheSpotlight();

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        String token = (String) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('token');");

        String uniqueIp = "10." + (int)(Math.random() * 255) + "." + (int)(Math.random() * 255) + ".1";
        // API valida enum en minúsculas: malware, intrusion, phishing, ddos, ransomware / low, medium, high, critical
        String body = "{\"type\":\"malware\",\"severity\":\"critical\",\"sourceIp\":\""
                + uniqueIp + "\",\"description\":\"Amenaza critica para test E2E\"}";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/api/threats"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .timeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String threatId = extractIdFromJson(response.body());
        assertThat(threatId)
                .as("Se esperaba un threatId válido en la respuesta: " + response.body())
                .isNotNull()
                .isNotBlank();

        actor.attemptsTo(
                CreateIncidentFromThreat.withThreatId(threatId)
        );
    }

    @Entonces("se muestra un mensaje de éxito al crear el incidente")
    public void incidentSuccessMessageIsShown() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.SUCCESS_ALERT, isVisible()).forNoMoreThan(15).seconds()
        );
        assertThat(IncidentTargets.SUCCESS_ALERT.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Y("la tabla de incidentes contiene al menos {int} registro")
    public void incidentTableHasAtLeast(int n) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(actor.asksFor(IncidentTableHasRecords.atLeast(n))).isTrue();
    }

    @Y("intenta crear un incidente con el UUID {string}")
    public void tryCreateIncidentWithUuid(String uuid) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                CreateIncidentFromThreat.withThreatId(uuid)
        );
    }

    @Entonces("se muestra un mensaje de error en la página de incidentes")
    public void incidentErrorMessageIsShown() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.ERROR_ALERT, isVisible()).forNoMoreThan(15).seconds()
        );
        assertThat(IncidentTargets.ERROR_ALERT.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Entonces("la barra de filtros es visible en la página de incidentes")
    public void filtersBarIsVisible() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.FILTERS_BAR, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(IncidentTargets.FILTERS_BAR.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    private String extractIdFromJson(String json) {
        Pattern pattern = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        pattern = Pattern.compile("\"threatId\"\\s*:\\s*\"([^\"]+)\"");
        matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
