package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.ThreatReportForm;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SubmitEmptyThreatForm implements Task {

    public static SubmitEmptyThreatForm attempt() {
        return instrumented(SubmitEmptyThreatForm.class);
    }

    @Override
    @Step("{0} intenta enviar el formulario de amenaza sin completar los campos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ThreatReportForm.SOURCE_IP_FIELD, isVisible()).forNoMoreThan(15).seconds(),
                Click.on(ThreatReportForm.SOURCE_IP_FIELD),
                Click.on(ThreatReportForm.DESCRIPTION_FIELD),
                Click.on(ThreatReportForm.SUBMIT_BUTTON)
        );
    }
}
