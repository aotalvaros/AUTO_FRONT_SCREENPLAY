package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.ThreatReportForm;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class SuccessMessage implements Question<Boolean> {

    public static SuccessMessage isDisplayed() {
        return new SuccessMessage();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return ThreatReportForm.SUCCESS_MESSAGE.resolveFor(actor).isCurrentlyVisible();
    }
}
