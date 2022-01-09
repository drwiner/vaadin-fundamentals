package org.vaadin.example;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.vaadin.example.databind.BindingForms;
import org.vaadin.example.databind.Validation;
import org.vaadin.example.datagrid.BackEndDataProvider;
import org.vaadin.example.datagrid.FilteringDataProvider;
import org.vaadin.example.routing.HomeView;
import org.vaadin.example.routing.LoginView;
import org.vaadin.example.routing.LogoutView;
import org.vaadin.example.routing.LotteryView;
import org.vaadin.example.styling.GridStylingView;

//@Route("")// this is commented out to allow a router link class to be default (route alias)


public class MainView extends AppLayout implements HasComponents, RouterLayout, BeforeEnterObserver {

    public MainView() {
        addToNavbar(new DrawerToggle());
        addToNavbar(new H2("Vaadin Examples") );

        final VerticalLayout menuBar = new VerticalLayout();

        menuBar.add(new RouterLink(Validation.TITLE, Validation.class));
        menuBar.add(new RouterLink(BindingForms.TITLE, BindingForms.class));
        menuBar.add(new RouterLink(FilteringDataProvider.TITLE, FilteringDataProvider.class));
        menuBar.add(new RouterLink(BackEndDataProvider.TITLE, BackEndDataProvider.class));
        menuBar.add(new RouterLink("Lottery Home", HomeView.class));
        menuBar.add(new RouterLink("Lottery Play", LotteryView.class));
        menuBar.add(new RouterLink(GridStylingView.TITLE, GridStylingView.class));
        menuBar.add(new RouterLink("Logout", LogoutView.class));

        addToDrawer(menuBar);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
//        if (VaadinSession.getCurrent().getAttribute("userLoggedIn") == null) {
//            VaadinSession.getCurrent().setAttribute("intendedPath", event.getLocation().getPath());
//            event.rerouteTo(LoginView.class);
//        }
    }
}
