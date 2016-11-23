package com.openlimit.ExampleApplication.ExampleFrontend.CLIClient;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;
import com.openlimit.ExampleApplication.ExampleFrontend.RestClient.RestClient;

public class CLIClient {

	private RestClient rc;
	private Scanner sc;
	
	private String protocol = "http";
	private String host = "localhost";
	private int port = 8080;

	public static void main(String[] args) {
		new CLIClient(args);
	}

	public CLIClient(String[] args) {
				
		if(Arrays.asList(args).contains("-h") || Arrays.asList(args).contains("-help")) {
			showHelp();
			System.exit(0);
		}
		
		for(int i = 0; i < args.length; i += 2) {
			switch (args[i]) {
			case "-host":
				if(!args[i+1].startsWith("-"))
					host = args[i+1];
				else	{
					System.err.println("ERROR: unknown Value \"" +args[i+1] 
							+ "\" for parameter \"" + args[i]);
					System.exit(0);
				}
				break;
				
			case "-port":
				if(!args[i+1].startsWith("-"))
					try {
						port = Integer.parseInt(args[i+1]);
					} catch (NumberFormatException e) {
						System.err.println("ERROR: unknown Value \"" +args[i+1] 
								+ "\" for parameter \"" + args[i]);
						System.err.println("ERROR: Port needs to be a number!");
						System.exit(0);
					}
				else	{
					System.err.println("ERROR: unknown Value \"" +args[i+1] 
							+ "\" for parameter \"" + args[i]);
					showHelp();
					System.exit(0);
				}
				break;
				
			case "-protocol":
				if(!args[i+1].startsWith("-"))
					protocol = args[i+1];
				else	{
					System.err.println("ERROR: unknown Value \"" +args[i+1] 
							+ "\" for parameter \"" + args[i]);
					System.exit(0);
				}
				break;

			default:
				System.err.println("ERROR: unknown parameter \"" + args[i]);
				System.exit(0);
				break;
			}
				
		}		
		
		rc = new RestClient(protocol, host, port, "RestExample/rest");
		sc = new Scanner(System.in);
		
		if(!rc.destinationReachable()) {
			System.err.println("ERROR: Destination " + protocol + "://" + host + ":" + port+ "/RestExample/rest" + " is not reachabe");
			showHelp();
			System.exit(0);
		}

		while (true) {
			System.out.println("##########################");
			System.out.println("### EXAMPLE-CLI-CLIENT ###");
			System.out.println("##########################");
			System.out.println("#    [U] - List Users    #");
			System.out.println("#    [T] - List Teams    #");
			System.out.println("#    [A] - Add User      #");
			System.out.println("#    [M] - Modify User   #");
			System.out.println("#    [H] - Show Help     #");
			System.out.println("#    [X] - Exit App      #");
			System.out.println("##########################");
			System.out.println();

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

			case "H":
				showHelp();
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

	private void showHelp() {
		System.out.println("#####################################################");
		System.out.println("########     EXAMPLE-CLI-CLIENT - HELP       ########");
		System.out.println("#####################################################");
		System.out.println("#                                                   #");
		System.out.println("# <<PARAMETERS>>                                    #");
		System.out.println("#                                                   #");
		System.out.println("# -host {VALUE}       (overrides default hostname)  #");
		System.out.println("# -port {VALUE}       (overrides default port)      #");
		System.out.println("# -protocol {VALUE}   (overrides default protocol)  #");
		System.out.println("#                                                   #");
		System.out.println("#---------------------------------------------------#");
		System.out.println("#                          |#########################");
		System.out.println("# <<DEFAULT VALUES>>       |#                       #");
		System.out.println("#                          |#   EXAMPLE-CLI-CLIENT  #");
		System.out.println("#  host : localhost        |#      Version 1.0      #");
		System.out.println("#  port : 8080             |#        © 2016         #");
		System.out.println("#  protocol : http         |#                       #");
		System.out.println("#                          |#                       #");
		System.out.println("#####################################################");
	}

	private String getUserInput() {
		System.out.print("--> ");
		return sc.nextLine();
	}

	private void listUsers() {
		try {
			List<User> users = rc.getUserAsList();

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
