package org.vaadin.example;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.databind.BindingForms;
import org.vaadin.example.databind.Validation;
import org.vaadin.example.datagrid.BackEndDataProvider;
import org.vaadin.example.datagrid.FilteringDataProvider;
import org.vaadin.example.routing.HomeView;
import org.vaadin.example.routing.LoginView;
import org.vaadin.example.routing.LogoutView;
import org.vaadin.example.routing.LotteryView;
import org.vaadin.example.security.SecurityService;
import org.vaadin.example.styling.GridStylingView;

import javax.annotation.security.PermitAll;

//@Route("")// this is commented out to allow a router link class to be default (route alias)
//@Route
//@AnonymousAllowed
public class MainView extends AppLayout implements HasComponents, RouterLayout, BeforeEnterObserver {

    private final SecurityService securityService;

    public MainView(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    public void createHeader(){

        Button logout = new Button("Log out", e -> securityService.logout());

        H2 logo = new H2("Vaadin App");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.addClassNames("py-0", "px-m");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);

    }


    public void createDrawer() {


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
        if (VaadinSession.getCurrent().getAttribute("userLoggedIn") == null) {
            VaadinSession.getCurrent().setAttribute("intendedPath", event.getLocation().getPath());
            event.rerouteTo(LoginView.class);
        }
    }
}
