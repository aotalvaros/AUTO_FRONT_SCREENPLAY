package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.DashboardTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class DashboardDisplayed implements Question<Boolean> {

    public static DashboardDisplayed isVisible() {
        return new DashboardDisplayed();
    }

    @Override
    @Step("{0} verifica que el dashboard es visible")
    public Boolean answeredBy(Actor actor) {
        return DashboardTargets.CONTAINER.resolveFor(actor).isCurrentlyVisible();
    }
}
