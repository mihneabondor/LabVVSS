package org.example.steps.serenity;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.example.pages.OrarePage;

public class OrareSteps extends UIInteractionSteps {

    OrarePage orarePage;

    @Step("Navigates to the Orare page")
    public void navigatesToOrarePage() {
        orarePage.openOrarePage();
    }

    @Step("Checks that the Orare page has loaded")
    public void orarePageIsDisplayed() {
        orarePage.isPageLoaded();
    }

    @Step("Clicks the '{0}' filter button")
    public void clicksFilterButton(String filterName) {
        orarePage.clickFilterButton(filterName);
    }

    @Step("Gets the active filter text")
    public String getsActiveFilterText() {
        return orarePage.getActiveFilterText();
    }

    @Step("Checks all visible routes are of type '{0}'")
    public boolean allVisibleRoutesAreOfType(String type) {
        return orarePage.allVisibleRoutesAreOfType(type);
    }

    @Step("Types '{0}' in the search field without submitting")
    public void typesInSearchField(String routeName) {
        orarePage.typeInSearchField(routeName);
    }

    @Step("Searches for route '{0}' and presses enter")
    public void searchesForRoute(String routeName) {
        orarePage.searchForRoute(routeName);
    }

    @Step("Checks that the page navigated to route '{0}'")
    public boolean isOnRouteDetailPage(String routeName) {
        return orarePage.isOnRouteDetailPage(routeName);
    }

    @Step("Clicks on route cell '{0}'")
    public void clicksOnRouteCell(String routeName) {
        orarePage.clickRouteCell(routeName);
    }

    @Step("Gets the alert message text")
    public String getsAlertText() {
        return orarePage.getAlertText();
    }

    @Step("Dismisses the alert")
    public void dismissesAlert() {
        orarePage.dismissAlert();
    }
}