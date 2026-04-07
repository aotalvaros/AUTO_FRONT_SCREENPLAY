package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.IncidentTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CreateButtonVisible implements Question<Boolean> {

    public static CreateButtonVisible onIncidentsPage() {
        return new CreateButtonVisible();
    }

    @Override
    @Step("{0} verifica si el botón Crear Incidente es visible")
    public Boolean answeredBy(Actor actor) {
        return IncidentTargets.CREATE_BUTTON.resolveFor(actor).isCurrentlyVisible();
    }
}
