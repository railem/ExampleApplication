package com.openlimit.ExampleApplication.ExampleFrontend.CLIClient;

import java.util.List;
import java.util.Scanner;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;
import com.openlimit.ExampleApplication.ExampleFrontend.RestClient.RestClient;

public class CLIClient {

	private RestClient rc;
	private Scanner sc;

	public static void main(String[] args) {
		new CLIClient();
	}

	public CLIClient() {
		rc = new RestClient("http", "localhost", 8080, "RestExample/rest");
		sc = new Scanner(System.in);

		while (true) {
			System.out.println("##########################");
			System.out.println("### EXAMPLE-CLI-CLIENT ###");
			System.out.println("##########################");
			System.out.println("#    [U] - List Users    #");
			System.out.println("#    [T] - List Teams    #");
			System.out.println("#    [A] - Add User      #");
			System.out.println("#    [M] - Modify User   #");
			System.out.println("#    [X] - Exit App      #");
			System.out.println("##########################");

			switch (getUserInput().toUpperCase()) {
			case "U":
				listUsers();
				break;

			case "T":
				listTeams();
				break;

			case "A":
				addUser();
				break;

			case "M":
				modUser();
				break;

			case "X":
				exitApp();
				break;

			default:
				System.out.println("invalid input!");
				break;
			}

		}
	}

	private String getUserInput() {
		System.out.print("--> ");
		return sc.nextLine();
	}

	private void listUsers() {
		try {
			List<User> users = rc.getUserList();

			System.out.println();
			System.out.format("%3s | %17s | %24s\n", "ID", "USERNAME", "EMAIL");
			System.out.println("---------------------------------------------------");
			users.forEach(
					user -> System.out.format("%3s | %17s | %24s\n", user.getId(), user.getUsername(), user.getEmail()));
			System.out.println();
		} catch (Throwable e) {
			System.err.println("ERROR: UserList not found!");
			System.err.println("Failed with: " + e.getMessage());
			return;
		}
	}

	private void listTeams() {
		try {
			List<Team> teams = rc.getTeamList();

			System.out.println();
			System.out.format("%3s | %17s | %13s\n", "ID", "TEAMNAME", "MEMBER COUNT");
			System.out.println("----------------------------------------");
			teams.forEach(team -> System.out.format("%3s | %17s | %13d\n", team.getTeamId(), team.getName(),
					team.getUsers().size()));
			System.out.println();
		} catch (Throwable e) {
			System.err.println("ERROR: UserList not found!");
			System.err.println("Failed with: " + e.getMessage());
			return;
		}
	}

	private void addUser() {
		System.out.println("Username of new User:");
		try {
			rc.addUser(getUserInput());
		} catch (Throwable e) {
			System.err.println("ERROR: User not added!");
			System.err.println("Failed with: " + e.getMessage());
			return;
		}
	}

	private void modUser() {
		System.out.println("Username of User:");
		User user;

		try {
			user = rc.getUser(getUserInput());
		} catch (Throwable e) {
			System.err.println("ERROR: User not found!");
			System.err.println("Failed with: " + e.getMessage());
			return;
		}
		
		if (user == null) {
			System.err.println("ERROR: User not found!");
			return;
		}

		System.out.format("%3s | %17s | %24s\n", "ID", "USERNAME", "EMAIL");
		System.out.println("---------------------------------------------------");
		System.out.format("%3s | %17s | %24s\n", user.getId(), user.getUsername(), user.getEmail());

		System.out.println("new Username (leave empty to keep old):");
		String usernameInput = getUserInput();
		if (!usernameInput.isEmpty())
			user.setUsername(usernameInput);

		System.out.println("new Email (leave empty to keep old):");
		String emailInput = getUserInput();
		if (!emailInput.isEmpty())
			user.setEmail(emailInput);

		try {
			rc.updateUser(user);
		} catch (Throwable e) {
			System.err.println("ERROR: User not updated!");
			System.err.println("Failed with: " + e.getMessage());
			return;
		}
	}

	private void exitApp() {
		System.exit(0);
	}
}
