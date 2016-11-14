package com.openlimit.ExampleApplication.ExampleFrontend.VaadinWui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.openlimit.ExampleApplication.ExampleFrontend.*;
import com.openlimit.ExampleApplication.ExampleFrontend.RestClient.RestClient;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        RestClient app = new RestClient();
        
        final TextField name = new TextField();
        name.setWidth("600px");
        name.setCaption("Rest Answer:");

        Button button = new Button("Request Users");
        button.addClickListener( e -> {
            name.setValue(app.getFromRest("users"));
        });
        
        Button button2 = new Button("Request Teams");
        button2.addClickListener( e -> {
        	name.setValue(app.getFromRest("teams"));
        });
        
        final TextField tf = new TextField();
        Button button3 = new Button("add User");
        button3.addClickListener( e -> {
        	app.postRest(tf.getValue());
        });
        
        
        final TextField jsonUser = new TextField("User JSON:");
        final TextField usertf = new TextField("get user by name:");
        Button button4 = new Button("get");
        button4.addClickListener( e -> {
        	String json = app.getUserByName(usertf.getValue());
        	jsonUser.setValue(json);
        });
        
        Button button5 = new Button("update");
        button5.addClickListener( e -> {
//        	String json = app.getUserByName(usertf.getValue());
//        	jsonUser.setValue(json);
        });

        
        layout.addComponents(name, button, button2, new HorizontalLayout(tf, button3),new HorizontalLayout(usertf, button4),
        		new HorizontalLayout(jsonUser, button5));
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
