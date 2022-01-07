package org.vaadin.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.databind.BindingForms;
import org.vaadin.example.databind.Validation;
import org.vaadin.example.datagrid.BackEndDataProvider;
import org.vaadin.example.datagrid.FilteringDataProvider;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
//@Route("")// this is commented out to allow a router link class to be default (route alias)
public class MainView extends AppLayout implements RouterLayout {

    public MainView() {
        addToNavbar(new DrawerToggle());
        addToNavbar(new H2("Data Binding Exercise") );

        final VerticalLayout menuBar = new VerticalLayout();
        menuBar.add(new RouterLink(Validation.TITLE, Validation.class));
        menuBar.add(new RouterLink(BindingForms.TITLE, BindingForms.class));

        menuBar.add(new RouterLink(FilteringDataProvider.TITLE, FilteringDataProvider.class));
        menuBar.add(new RouterLink(BackEndDataProvider.TITLE, BackEndDataProvider.class));

        addToDrawer(menuBar);

    }
}
