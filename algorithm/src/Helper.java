import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper {

	public static String[] readByLine(InputStream input, String end) {
		ArrayList<String> content = new ArrayList<String>();
		Scanner scanner = new Scanner(input);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.trim().equals(end)) {
				break;
			}
			content.add(line);
		}
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNext()) {
			sb.append(scanner.next());
		}
		if (!sb.toString().trim().equals("")) {
			content.add(sb.toString());
		}
		return content.toArray(new String[0]);
	}

	public static String[] readFileByLine(String file)
			throws FileNotFoundException {
		return readByLine(new FileInputStream(file), null);
	}

}