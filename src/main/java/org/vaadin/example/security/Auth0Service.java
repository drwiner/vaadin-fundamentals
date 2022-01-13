package org.vaadin.example.security;


import com.auth0.Tokens;
import com.auth0.json.auth.UserInfo;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.SpringVaadinServletService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Auth0Service extends SpringVaadinServletService {

    public Auth0Service(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration, ApplicationContext context) throws ServiceException {
        super(servlet, deploymentConfiguration, context);
    }

    private static void sessionInit(SessionInitEvent event) {
        event.getSession().addRequestHandler(Auth0Service::handleRequest);

    }

    private static final String AUTH0_CALLBACK_HANDLE = "/callback";
    private static final String STATE = "state";
    private static final String CODE = "code";

    /*
     * This is what is added by @drwiner 01-2022
     */

    private static boolean handleRequest(VaadinSession vaadinSession, VaadinRequest request, VaadinResponse response) throws IOException {
        if (request.getPathInfo().equals(AUTH0_CALLBACK_HANDLE)) {
            if (request.getParameterMap().containsKey(STATE) && request.getParameterMap().containsKey(CODE)) {
                try {
                    Tokens tokens = AuthenticationControllerProvider.getInstance().handle((HttpServletRequest) request);
                    UserInfo userInfo = Auth0Util.resolveUser(tokens.getAccessToken());
                    Auth0Session session = (Auth0Session) vaadinSession;
                    if (session == null) {
                        System.err.println("session error");
                    } else {
                        session.setAuth0Info(tokens, userInfo);
                    }
                    ((HttpServletResponse) response).sendRedirect("");
                    return true;
                } catch (Exception eprime) {
                    ((HttpServletResponse) response).sendRedirect("error");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected List<RequestHandler> createRequestHandlers()
            throws ServiceException {
        List<RequestHandler> list = super.createRequestHandlers();
        addSessionInitListener(Auth0Service::sessionInit);
        return list;
    }

    @Override
    protected VaadinSession createVaadinSession(VaadinRequest request) {
        return new Auth0Session(this);
    }
}
