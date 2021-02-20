package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

public class StudentDB {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);

		StudentDatabase sdb = new StudentDatabase(lines);
		int firstNameLength = 0, lastNameLength = 0, jmbagLength = 0;
		try (Scanner sc = new Scanner(System.in)) {

			while (true) {
				System.out.format("> ");
				String query = sc.nextLine();

				if (query.equals("exit")) {
					System.out.println("Goodbye");
					break;
				}

				if (!query.trim().startsWith("query")) {
					System.out.println("Command is not valid.");
					System.out.println("Valid commands are: query and exit!");
					continue;
				}

				QueryParser parser;

				try {
					parser = new QueryParser(query.trim().substring(5));
				} catch (QueryParserException err) {
					System.out.println(err.getMessage());
					continue;
				} catch (Exception e) {
					System.out.println("Query is not parseable! An exception occured. Check it!");
					continue;
				}

				if (parser.isDirectQuery()) {
					StudentRecord r = sdb.forJMBAG(parser.getQueriedJMBAG());
					System.out.println("Using index for record retrieval.");

					printEdge(r.getFirstName().length(), r.getLastName().length(), r.getJmbag().length());
					printRecord(r.getFirstName().length(), r.getLastName().length(), r.getJmbag().length(), r);
					printEdge(r.getFirstName().length(), r.getLastName().length(), r.getJmbag().length());

					System.out.println("Records selected: 1");
				} else {
					List<StudentRecord> listOfRecords = sdb.filter(new QueryFilter(parser.getQuery()));

					if (listOfRecords.size() > 0) {
						firstNameLength = getMax(FieldValueGetters.FIRST_NAME, listOfRecords);
						lastNameLength = getMax(FieldValueGetters.LAST_NAME, listOfRecords);
						jmbagLength = getMax(FieldValueGetters.JMBAG, listOfRecords);

						printEdge(firstNameLength, lastNameLength, jmbagLength);

						for (StudentRecord r : listOfRecords) {
							printRecord(firstNameLength, lastNameLength, jmbagLength, r);
						}

						printEdge(firstNameLength, lastNameLength, jmbagLength);

						System.out.println("Records selected: " + listOfRecords.size());

					} else {
						System.out.println("Records selected: 0");
					}
				}
			}
		}
	}

	private static int getMax(IfieldValueGetter getter, List<StudentRecord> listOfRecords) {
		OptionalInt optInt = listOfRecords.stream()
				.mapToInt(m -> getter.get(m).length()).max();

		if (optInt.isPresent()) {
			return optInt.getAsInt();
		}
		
		return 0;
	}

	private static void printRecord(int firstNameLength, int lastNameLength, int jmbagLength, StudentRecord record) {
		String str = new String("| " + record.getJmbag());

		for (int i = 0; i < jmbagLength + 1 - record.getJmbag().length(); i++) {
			str += " ";
		}

		str += "| " + record.getLastName();

		for (int i = 0; i < lastNameLength + 1 - record.getLastName().length(); i++) {
			str += " ";
		}

		str += "| " + record.getFirstName();

		for (int i = 0; i < firstNameLength + 1 - record.getFirstName().length(); i++) {
			str += " ";
		}

		str += "| " + record.getFinalGrade() + " |";

		System.out.println(str);
	}

	private static void printEdge(int firstNameLength, int lastNameLength, int jmbagLength) {
		String firstString = new String("+");

		for (int i = 0; i < jmbagLength + 2; i++) {
			firstString += "=";
		}

		firstString += "+";

		for (int i = 0; i < lastNameLength + 2; i++) {
			firstString += "=";
		}

		firstString += "+";

		for (int i = 0; i < firstNameLength + 2; i++) {
			firstString += "=";
		}

		firstString += "+";

		for (int i = 0; i < 3; i++) {
			firstString += "=";
		}

		firstString += "+";

		System.out.println(firstString);
	}
}
