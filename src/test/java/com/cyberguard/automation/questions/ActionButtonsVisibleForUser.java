package com.cyberguard.automation.questions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ActionButtonsVisibleForUser implements Question<Boolean> {

    private final String username;

    public ActionButtonsVisibleForUser(String username) {
        this.username = username;
    }

    public static ActionButtonsVisibleForUser withUsername(String username) {
        return new ActionButtonsVisibleForUser(username);
    }

    @Override
    @Step("{0} verifica si los botones de acción son visibles para el usuario '#username'")
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> rows = driver.findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                List<WebElement> editBtns = row.findElements(By.cssSelector(".btn-sm.btn-edit"));
                List<WebElement> toggleBtns = row.findElements(By.cssSelector(".btn-sm.btn-deactivate, .btn-sm.btn-activate"));
                return !editBtns.isEmpty() || !toggleBtns.isEmpty();
            }
        }
        return false;
    }
}
