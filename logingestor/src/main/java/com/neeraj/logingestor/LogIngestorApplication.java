package com.neeraj.logingestor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.model.LogSearchCriteriaRequest;
import com.neeraj.logingestor.service.LogService;

@SpringBootApplication
public class LogIngestorApplication implements CommandLineRunner {
	
	@Autowired
	private LogService logService;

	public static void main(String[] args) {
		SpringApplication.run(LogIngestorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		while (true) {
			System.out.println("Menu:");
			System.out.println("1. Search logs");
			System.out.println("2. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume the newline

			switch (choice) {
			case 1:
				String level;
				String message;
				String resourceId;
				String traceId;
				String spanId;
				String commit;
				String fromDate;
				String toDate;

				LogSearchCriteriaRequest logRequest = new LogSearchCriteriaRequest();
				System.out.println("Enter the below filters from search : ");
				System.out.print("----->  Enter log level :");
				level = scanner.nextLine();
				if (!level.equals("")) {
					logRequest.setLevel(level);
				}
				System.out.println();

				System.out.print("----->  Enter log Message :");
				message = scanner.nextLine();
				if (!message.equals("")) {
					logRequest.setMessage(message);
				}
				System.out.println();

				System.out.print("----->  Enter log resourceId :");
				resourceId = scanner.nextLine();
				if (!resourceId.equals("")) {
					logRequest.setResourceId(resourceId);
				}
				System.out.println();

				System.out.print("----->  Enter log fromDate :");
				fromDate = scanner.nextLine();
				if (!fromDate.equals("")) {
					logRequest.setFromDate(LocalDateTime.parse(fromDate, formatter));
				}
				System.out.println();

				System.out.print("----->  Enter log toDate :");
				toDate = scanner.nextLine();
				if (!toDate.equals("")) {
					logRequest.setFromDate(LocalDateTime.parse(toDate, formatter));
				}
				System.out.println();
				
				System.out.println("Waiting for input");
				List<LogRequest> searchResultList = logService.searchLogsByCriteria(logRequest);
				
				System.out.println(searchResultList);
				break;
			case 2:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}

	}

}
