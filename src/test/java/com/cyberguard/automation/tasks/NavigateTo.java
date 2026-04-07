package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.SidebarTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class NavigateTo implements Task {

    private final String destination;
    private final String linkText;

    public NavigateTo(String destination, String linkText) {
        this.destination = destination;
        this.linkText = linkText;
    }

    public static NavigateTo theIncidentsPage() {
        return instrumented(NavigateTo.class, "Incidentes", "Incidentes");
    }

    public static NavigateTo theUsersPage() {
        // El sidebar del frontend usa "Gestión Usuarios" (sin "de") — ver TEST_PLAN sección 6.2
        return instrumented(NavigateTo.class, "Gestión Usuarios", "Gestión Usuarios");
    }

    @Override
    @Step("{0} navega a #destination")
    public <T extends Actor> void performAs(T actor) {
        Target navLinkByText = Target.the("enlace de navegación '" + linkText + "'")
                .located(By.xpath("//a[contains(@class,'sidebar__link') and @title='" + linkText + "']"));

        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.SIDEBAR, isVisible()).forNoMoreThan(10).seconds(),
                WaitUntil.the(navLinkByText, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(navLinkByText)
        );
    }
}
