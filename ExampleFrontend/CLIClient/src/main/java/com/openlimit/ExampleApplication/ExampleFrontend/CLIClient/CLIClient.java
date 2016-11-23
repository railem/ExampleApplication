package com.openlimit.ExampleApplication.ExampleFrontend.CLIClient;

import java.util.List;
import java.util.Scanner;

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
		
		while(true) {
			System.out.println("####################");
			System.out.println("# [U] - List Users #");
			System.out.println("# [T] - List Teams #");
			System.out.println("# [A] - Add User   #");
			System.out.println("# [G] - Get User   #");
			System.out.println("# [X] - Exit App   #");
			System.out.println("####################");
			System.out.print("--> ");
			
			switch (sc.nextLine().toUpperCase()) {
			case "U":
				listUsers();
				break;
				
			case "T":
				listTeams();
				break;
				
			case "A":
				addUser();
				break;
				
			case "G":
				getUser();
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
	
	private void listUsers() {
		List<User> users = rc.getUserList();
	}

	private void listTeams() {
		// TODO Auto-generated method stub
		
	}

	private void addUser() {
		// TODO Auto-generated method stub
		
	}

	private void getUser() {
		// TODO Auto-generated method stub
		
	}

	private void exitApp() {
		// TODO Auto-generated method stub
		
	}
}
