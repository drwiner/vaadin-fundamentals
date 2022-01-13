package org.vaadin.example.routing;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.MainView;

import javax.annotation.security.PermitAll;
import java.nio.file.AccessDeniedException;

@AnonymousAllowed
@Route(value="error", layout = MainView.class)
public class InvalidAccessErrorView extends Div  implements HasErrorParameter<AccessDeniedException> {

    public static final String ERROR_TEXT = "Must Login first.";
    public InvalidAccessErrorView() {
        setText(ERROR_TEXT);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<AccessDeniedException> parameter) {
//        setText(ERROR_TEXT);
        return 500;
    }


}
