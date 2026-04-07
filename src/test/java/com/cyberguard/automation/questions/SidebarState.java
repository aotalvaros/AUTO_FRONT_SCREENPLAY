package com.cyberguard.automation.questions;

import com.cyberguard.automation.ui.SidebarTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SidebarState {

    public static Question<Boolean> isCollapsed() {
        return new Question<Boolean>() {
            @Override
            @Step("{0} verifica si el sidebar está colapsado")
            public Boolean answeredBy(Actor actor) {
                WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                List<WebElement> sidebars = driver.findElements(
                        org.openqa.selenium.By.cssSelector(".sidebar--collapsed"));
                return !sidebars.isEmpty();
            }
        };
    }

    public static Question<Boolean> hasItem(String text) {
        return new Question<Boolean>() {
            @Override
            @Step("{0} verifica si el sidebar tiene el ítem '{0}'")
            public Boolean answeredBy(Actor actor) {
                WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                List<WebElement> labels = driver.findElements(
                        org.openqa.selenium.By.cssSelector(".nav-label"));
                for (WebElement label : labels) {
                    if (label.getText().trim().toLowerCase().contains(text.trim().toLowerCase())) {
                        return true;
                    }
                }
                // También buscar en .sidebar__link por si el texto no está en .nav-label
                List<WebElement> links = driver.findElements(
                        org.openqa.selenium.By.cssSelector(".sidebar__link"));
                for (WebElement link : links) {
                    if (link.getText().trim().toLowerCase().contains(text.trim().toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static Question<String> activeItemText() {
        return new Question<String>() {
            @Override
            @Step("{0} obtiene el texto del ítem activo del sidebar")
            public String answeredBy(Actor actor) {
                try {
                    return SidebarTargets.ACTIVE_LINK.resolveFor(actor).getText().trim();
                } catch (Exception e) {
                    return "";
                }
            }
        };
    }
}
