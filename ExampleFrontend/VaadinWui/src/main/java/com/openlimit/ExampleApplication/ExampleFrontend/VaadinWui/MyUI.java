package com.openlimit.ExampleApplication.ExampleFrontend.VaadinWui;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.HeaderRow;
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
	
	List<User> users;
	BeanItemContainer<User> ds;
	Grid grid;
	
	List<Team> teams;
	BeanItemContainer<Team> ds2;
	Grid grid2;
	
	RestClient app = new RestClient("http", "localhost", 8080, "RestExample/rest");
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		users = app.getUserList();		
		teams = app.getTeamList();
		
		VerticalLayout layout = new VerticalLayout();

		
		HorizontalLayout tableLayout = new HorizontalLayout();
		ds = new BeanItemContainer<User>(User.class, users);
		grid = new Grid("Users", ds);
		tableLayout.addComponent(grid);
		grid.getColumn("username").setMinimumWidth(200);
		grid.setColumnOrder("id", "username", "email");
		grid.setImmediate(true);
		
		HeaderRow row = grid.prependHeaderRow();
		row.join("username", "email").setHtml("<b>Editable</b>");
		FooterRow footer = grid.appendFooterRow();
		
		int totalId = 0;
		for(User u : users){
			totalId+=u.getId();
		}
		if(totalId!=0)
		footer.getCell("id").setText("ø "+(totalId/users.size()));
		
		grid.setEditorEnabled(true);
		grid.setEditorSaveCaption("Save my data, please!");
		 
		grid.addSelectionListener(e -> {
			BeanItem<User> item = ds.getItem(grid.getSelectedRow());
		    e.getSource();   
	    });

		ds2 = new BeanItemContainer<Team>(Team.class, teams);
		grid2 = new Grid("Teams", ds2);
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
			refreshData();
		});
		
		Button update = new Button("update");
		update.addClickListener(e -> {
			User us = new User();
			us.setId(u.getId());
			us.setEmail(email.getValue());
			us.setUsername(username.getValue());
			
			app.updateUser(us);
			refreshData();
		});

		layout.addComponent(tableLayout);
		layout.addComponents(user, getUser, addser, username, email, update);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	private void refreshData() {
		users = app.getUserList();
		ds.removeAllItems();
		ds.addAll(users);
		grid.markAsDirty();
		
		teams = app.getTeamList();
		ds2.removeAllItems();
		ds2.addAll(teams);
		grid2.markAsDirty();
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
	public static class MyUIServlet extends VaadinServlet {
	}
}
