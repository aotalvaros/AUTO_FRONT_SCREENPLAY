package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.SidebarTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ExpandSidebar implements Task {

    public static ExpandSidebar now() {
        return instrumented(ExpandSidebar.class);
    }

    @Override
    @Step("{0} expande el menú lateral")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.TOGGLE_BUTTON, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(SidebarTargets.TOGGLE_BUTTON)
        );
    }
}
