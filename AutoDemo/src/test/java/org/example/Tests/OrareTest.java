package org.example.Tests;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.steps.serenity.OrareSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class OrareTest {

    @Managed
    WebDriver driver;

    @Steps
    OrareSteps orareSteps;

    @Test
    public void userCanAccessTheOrarePage() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();

        assertThat(driver.getTitle())
                .as("Browser tab title should contain Busify")
                .contains("Busify");
    }

    @Test
    public void filteringByAutobuzeShowsOnlyBuses() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.clicksFilterButton("Autobuze");

        assertThat(orareSteps.getsActiveFilterText())
                .as("Active filter should be Autobuze")
                .contains("Autobuze");

        assertThat(orareSteps.allVisibleRoutesAreOfType("autobuze"))
                .as("All visible routes should be buses")
                .isTrue();
    }

    @Test
    public void filteringByTroleibuzeShowsOnlyTrolleys() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.clicksFilterButton("Troleibuze");

        assertThat(orareSteps.getsActiveFilterText())
                .as("Active filter should be Troleibuze")
                .contains("Troleibuze");

        assertThat(orareSteps.allVisibleRoutesAreOfType("troleibuze"))
                .as("All visible routes should be trolleybuses")
                .isTrue();
    }

    @Test
    public void filteringByTramvaieShowsOnlyTrams() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.clicksFilterButton("Tramvaie");

        assertThat(orareSteps.getsActiveFilterText())
                .as("Active filter should be Tramvaie")
                .contains("Tramvaie");

        assertThat(orareSteps.allVisibleRoutesAreOfType("tramvaie"))
                .as("All visible routes should be trams")
                .isTrue();
    }

    @Test
    public void searchingForValidRouteNavigatesToItsTimetable() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.searchesForRoute("42");

        assertThat(orareSteps.isOnRouteDetailPage("42"))
                .as("Should navigate to the timetable page for route 42")
                .isTrue();
    }

    @Test
    public void searchingForRouteAndClickingCellNavigatesToTimetable() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.typesInSearchField("42");
        orareSteps.clicksOnRouteCell("42");

        assertThat(orareSteps.isOnRouteDetailPage("42"))
                .as("Should navigate to the timetable page for route 42 after clicking the cell")
                .isTrue();
    }

    @Test
    public void clickingCellNavigatesToTimetable() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.clicksOnRouteCell("42");
        assertThat(orareSteps.isOnRouteDetailPage("42"))
                .as("Should navigate to the timetable page for route 42 after clicking the cell")
                .isTrue();
    }

    @Test
    public void searchingForInvalidRouteShowsAlert() {
        orareSteps.navigatesToOrarePage();
        orareSteps.orarePageIsDisplayed();
        orareSteps.searchesForRoute("67");

        assertThat(orareSteps.getsAlertText())
                .as("Should show invalid route alert")
                .isEqualTo("Linie invalida");

        orareSteps.dismissesAlert();
    }
}