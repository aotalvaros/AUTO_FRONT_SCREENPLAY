package com.cyberguard.automation.questions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IncidentTableHasRecords implements Question<Boolean> {

    private final int minimumRows;

    public IncidentTableHasRecords(int minimumRows) {
        this.minimumRows = minimumRows;
    }

    public static IncidentTableHasRecords atLeast(int n) {
        return new IncidentTableHasRecords(n);
    }

    @Override
    @Step("{0} verifica que la tabla de incidentes tiene al menos #minimumRows fila(s)")
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> rows = driver.findElements(By.cssSelector(".data-table tbody tr"));
        return rows.size() >= minimumRows;
    }
}
