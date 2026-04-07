package com.cyberguard.automation.stepdefinitions;

import com.cyberguard.automation.questions.SidebarState;
import com.cyberguard.automation.tasks.CollapseSidebar;
import com.cyberguard.automation.tasks.ExpandSidebar;
import com.cyberguard.automation.ui.SidebarTargets;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class SidebarStepDefinitions {

    @Entonces("el sidebar de navegación es visible")
    public void sidebarIsVisible() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.SIDEBAR, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(SidebarTargets.SIDEBAR.resolveFor(actor).isCurrentlyVisible()).isTrue();
    }

    @Entonces("el sidebar contiene el ítem de navegación {string}")
    public void sidebarContainsItem(String itemText) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.SIDEBAR, isVisible()).forNoMoreThan(10).seconds()
        );
        assertThat(actor.asksFor(SidebarState.hasItem(itemText))).isTrue();
    }

    @Cuando("colapsa el menú lateral")
    public void collapsesSidebar() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                CollapseSidebar.now()
        );
    }

    @Y("expande el menú lateral")
    public void expandsSidebar() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ExpandSidebar.now()
        );
    }

    @Entonces("el menú lateral está colapsado")
    public void sidebarIsCollapsed() {
        Actor actor = OnStage.theActorInTheSpotlight();
        assertThat(actor.asksFor(SidebarState.isCollapsed())).isTrue();
    }

    @Entonces("el menú lateral está expandido")
    public void sidebarIsExpanded() {
        Actor actor = OnStage.theActorInTheSpotlight();
        assertThat(actor.asksFor(SidebarState.isCollapsed())).isFalse();
    }

    @Y("recarga la página")
    public void reloadsThePage() {
        Actor actor = OnStage.theActorInTheSpotlight();
        BrowseTheWeb.as(actor).getDriver().navigate().refresh();
        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.SIDEBAR, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Entonces("el menú lateral sigue colapsado")
    public void sidebarRemainsCollapsed() {
        Actor actor = OnStage.theActorInTheSpotlight();
        assertThat(actor.asksFor(SidebarState.isCollapsed())).isTrue();
    }

    @Entonces("el ítem {string} está resaltado como activo en el sidebar")
    public void itemIsHighlightedAsActive(String expectedItem) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(SidebarTargets.ACTIVE_LINK, isVisible()).forNoMoreThan(10).seconds()
        );
        String activeText = actor.asksFor(SidebarState.activeItemText());
        assertThat(activeText).containsIgnoringCase(expectedItem);
    }
}
