package org.vaadin.example.routing;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.security.Auth0Session;

@Route("logout")
public class LogoutView extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        VaadinSession.getCurrent().getSession().invalidate();
        Auth0Session.getCurrent().logout();
        UI.getCurrent().getPage().setLocation("");
    }
}
