package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.ThreatReportForm;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ReportThreat implements Task {

    private final String type;
    private final String severity;
    private final String sourceIp;
    private final String description;

    public ReportThreat(String type, String severity, String sourceIp, String description) {
        this.type = type;
        this.severity = severity;
        this.sourceIp = sourceIp;
        this.description = description;
    }

    public static ReportThreat withData(String type, String severity, String sourceIp, String description) {
        return instrumented(ReportThreat.class, type, severity, sourceIp, description);
    }

    @Override
    @Step("{0} reporta una amenaza de tipo #type con severidad #severity")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ThreatReportForm.TYPE_SELECT, isVisible()).forNoMoreThan(15).seconds(),
                SelectFromOptions.byValue(type).from(ThreatReportForm.TYPE_SELECT),
                SelectFromOptions.byValue(severity).from(ThreatReportForm.SEVERITY_SELECT),
                Enter.theValue(sourceIp).into(ThreatReportForm.SOURCE_IP_FIELD),
                Enter.theValue(description).into(ThreatReportForm.DESCRIPTION_FIELD),
                Click.on(ThreatReportForm.SUBMIT_BUTTON)
        );
    }
}
