package com.openlimit.ExampleApplication.ExampleFrontend.VaadinWui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;
import com.openlimit.ExampleApplication.ExampleFrontend.*;
import com.openlimit.ExampleApplication.ExampleFrontend.RestClient.RestClient;

@Theme("mytheme")
public class MyUI extends UI {

	private User u;
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		VerticalLayout layout = new VerticalLayout();
		RestClient app = new RestClient();

		HorizontalLayout tableLayout = new HorizontalLayout();
		BeanItemContainer<User> ds = new BeanItemContainer<User>(User.class, app.getUserList());
		Grid grid = new Grid("Users", ds);
		tableLayout.addComponent(grid);

		BeanItemContainer<Team> ds2 = new BeanItemContainer<Team>(Team.class, app.getTeamList());
		Grid grid2 = new Grid("Teams", ds2);
		tableLayout.addComponent(grid2);

		TextField username = new TextField("Username:");
		TextField email = new TextField("Email:");
		
		TextField user = new TextField("User:");
		Button getUser = new Button("getUser");
		getUser.addClickListener(e -> {
			u = app.getUser(user.getValue());
			username.setValue(u.getUsername());
			email.setValue(u.getEmail());
		});
		
		Button addser = new Button("addUser");
		addser.addClickListener(e -> {
			app.addUser(user.getValue());
		});
		
		Button update = new Button("update");
		update.addClickListener(e -> {
			User us = new User();
			us.setId(u.getId());
			us.setEmail(email.getValue());
			us.setUsername(username.getValue());
			
			app.updateUser(us);
		});

		layout.addComponent(tableLayout);
		layout.addComponents(user, getUser, addser, username, email, update);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
