package org.vaadin.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.databind.BindingForms;
import org.vaadin.example.databind.Validation;
import org.vaadin.example.datagrid.BackEndDataProvider;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
//@Route("")
public class MainView extends AppLayout implements RouterLayout {

    public MainView() {
        addToNavbar(new DrawerToggle());
        addToNavbar(new H2("Data Binding Exercise") );

        final VerticalLayout menuBar = new VerticalLayout();
        menuBar.add(new RouterLink(Validation.TITLE, Validation.class));
        menuBar.add(new RouterLink(BindingForms.TITLE, BindingForms.class));

        menuBar.add(new RouterLink(BackEndDataProvider.TITLE, BackEndDataProvider.class));

        addToDrawer(menuBar);

    }

//    public MainView() {
//        // Use TextField for standard text input
//        TextField textField = new TextField("Your name");
//        textField.addThemeName("bordered");
//        // Button click listeners can be defined as lambda expressions
//        GreetService greetService = new GreetService();
//        Button button = new Button("Say hello", e -> Notification
//                .show(greetService.greet(textField.getValue())));
//
//        // Theme variants give you predefined extra styles for components.
//        // Example: Primary button is more prominent look.
//        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        // You can specify keyboard shortcuts for buttons.
//        // Example: Pressing enter in this view clicks the Button.
//        button.addClickShortcut(Key.ENTER);
//
//        // Use custom CSS classes to apply styling. This is defined in
//        // shared-styles.css.
//        addClassName("centered-content");
//
//        add(textField, button);
//    }
}
