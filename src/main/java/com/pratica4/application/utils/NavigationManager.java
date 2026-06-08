package com.pratica4.application.utils;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class NavigationManager {

    private String page;

    public void navigateToPage(Pane container, Parent pageUrl) {
            container.getChildren().clear();
            container.getChildren().add(pageUrl);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
