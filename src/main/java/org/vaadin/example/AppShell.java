package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
//@Theme("my-theme")
@Theme(themeClass= Lumo.class, variant=Lumo.DARK)
@CssImport(value = "./styles/vaadin-grid-warning.css", themeFor = "vaadin-grid")
//@CssImport("./styles/shared-styles.css")
public class AppShell implements AppShellConfigurator {
}
