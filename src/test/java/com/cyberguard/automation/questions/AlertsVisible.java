package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.DashboardAlerts;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class AlertsVisible implements Question<Boolean> {

    public static AlertsVisible onDashboard() {
        return new AlertsVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !DashboardAlerts.ALERT_CARDS.resolveAllFor(actor).isEmpty();
    }
}
