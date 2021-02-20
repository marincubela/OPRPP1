package hr.fer.oprpp1.hw04.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;
import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class StudentDatabaseTest {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);

		StudentDatabase sdb = new StudentDatabase(lines);
		System.out.println(sdb.forJMBAG("0000000024").getFirstName()); // Expecting Karlović
		System.out.println(sdb.forJMBAG("0000000024").getLastName()); // Expecting Đive
		System.out.println(sdb.forJMBAG("0000000024").getFinalGrade()); // Expecting 5

		for (StudentRecord record : sdb.filter(x -> true)) {
			System.out.println(record);
		}

		System.out.println("------------------");

		for (StudentRecord record : sdb.filter(x -> false)) {
			System.out.println(record);
		}

		System.out.println("------------------");

		IComparisonOperator oper = ComparisonOperators.LESS;
		System.out.println(oper.satisfied("Iva", "Marin")); // true, since Ana < Jasna

		oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true

		StudentRecord record = sdb.forJMBAG("0000000012");
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));

		System.out.println("------------------");

		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), // returns
																											// lastName
																											// from
																											// given
																											// record
				expr.getStringLiteral());// returns "Bos*"

		System.out.println(recordSatisfies);


		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1

		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
//		System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2

		System.out.println("+++++++++++++++++++++++++++++++++++");

		QueryParser parser = new QueryParser("lastName LIKE \"L*ić\"");
		if (parser.isDirectQuery()) {
			StudentRecord r = sdb.forJMBAG(parser.getQueriedJMBAG());
			System.out.println(r);
		} else {
			for (StudentRecord r : sdb.filter(new QueryFilter(parser.getQuery()))) {
				System.out.println(r);
			}
		}

	}
}
