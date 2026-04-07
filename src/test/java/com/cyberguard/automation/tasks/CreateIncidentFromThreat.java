package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.IncidentTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CreateIncidentFromThreat implements Task {

    private final String threatId;

    public CreateIncidentFromThreat(String threatId) {
        this.threatId = threatId;
    }

    public static CreateIncidentFromThreat withThreatId(String threatId) {
        return instrumented(CreateIncidentFromThreat.class, threatId);
    }

    @Override
    @Step("{0} crea un incidente a partir de la amenaza #threatId")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(IncidentTargets.CREATE_BUTTON, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(IncidentTargets.CREATE_BUTTON),
                WaitUntil.the(IncidentTargets.FORM_CARD, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(threatId).into(IncidentTargets.THREAT_ID_INPUT),
                Click.on(IncidentTargets.SUBMIT_BUTTON)
        );
    }
}
