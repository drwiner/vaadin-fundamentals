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

//@Route("")// this is commented out to allow a router link class to be default (route alias)
public class MainView extends AppLayout implements RouterLayout {

    public MainView() {
        addToNavbar(new DrawerToggle());
        addToNavbar(new H2("Vaadin Examples") );

        final VerticalLayout menuBar = new VerticalLayout();

//        menuBar.add(new RouterLink(Validation.TITLE, Validation.class));
//        menuBar.add(new RouterLink(BindingForms.TITLE, BindingForms.class));
//        menuBar.add(new RouterLink(FilteringDataProvider.TITLE, FilteringDataProvider.class));
//        menuBar.add(new RouterLink(BackEndDataProvider.TITLE, BackEndDataProvider.class));
//        menuBar.add(new RouterLink("main_layout", MainLayout.class));

        addToDrawer(menuBar);

    }
}
