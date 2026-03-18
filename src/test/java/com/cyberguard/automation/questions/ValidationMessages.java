package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.ThreatReportForm;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ValidationMessages implements Question<Boolean> {

    public static ValidationMessages areDisplayed() {
        return new ValidationMessages();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !ThreatReportForm.VALIDATION_ERRORS.resolveAllFor(actor).isEmpty();
    }
}
