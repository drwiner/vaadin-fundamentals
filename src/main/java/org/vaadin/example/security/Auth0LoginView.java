package org.vaadin.example.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.MainView;

@AnonymousAllowed
@Route(value="login", layout = MainView.class)
public class Auth0LoginView extends VerticalLayout implements AfterNavigationObserver {

    private ProgressBar spinner;
    private H1 errorLabel;
    private H2 errorDescLabel;

    public Auth0LoginView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        spinner = new ProgressBar();
        spinner.setIndeterminate(true);
        spinner.setWidth("200px");
        spinner.setHeight("200px");
        layout.add(spinner);
        //layout.setComponentAlignment(spinner, Alignment.MIDDLE_CENTER);

        errorLabel = new H1("Something went wrong :(");
        errorLabel.setWidth("100%");
        errorLabel.setVisible(false);

        errorDescLabel = new H2("n/a");
        errorDescLabel.setWidth("100%");
        errorDescLabel.setVisible(false);

        layout.add(errorLabel, errorDescLabel);

    }

    private void checkAuthentication(VaadinRequest request) {
        VaadinServletRequest servletRequest = (VaadinServletRequest) request;
        try {
            String url = AuthenticationControllerProvider.getInstance().buildAuthorizeUrl(servletRequest, Auth0Util.getCallback()).withScope("openid email profile").build();//  buildAuthorizeUrl(servletRequest, Auth0Util.getLoginURL()).build();
            UI.getCurrent().getPage().setLocation(url);

        }catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Throwable t) {
        spinner.setVisible(false);
        errorLabel.setVisible(true);
        errorDescLabel.setVisible(true);
        errorDescLabel.getElement().setText(t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        checkAuthentication(VaadinService.getCurrentRequest());
    }

}
