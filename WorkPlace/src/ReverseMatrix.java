import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseMatrix {

	private static final String INPUT_DATA_FILE = "E:\\JavaWorkSpace\\WorkPlace\\resources\\OutputDataFile.txt";
	private static final String OUTPUT_DATA_FILE = "E:\\JavaWorkSpace\\WorkPlace\\resources\\OutputTestDataFile.txt";

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new FileReader(INPUT_DATA_FILE))) {
			String line;
			List<String[]> list = new ArrayList<String[]>();

			File result = new File(OUTPUT_DATA_FILE);
			result.createNewFile();
			FileWriter writer = new FileWriter(result);

			while ((line = br.readLine()) != null)
				list.add(line.split(" +"));

			String[][] data = new String[list.size()][];
			list.toArray(data);
			br.close();

			int matrixCount = list.size() / 4;

			int cnt = matrixCount;
			int k = 0;
			int j = 0;
			int i = 0;
			int clmbkp = 0;
			int rowbkp = 0;
			int test = 0;
			do {
				String tmp[][] = new String[4][4];
				for (; k < matrixCount; k++) {
					int rowcnt = 0;
					i = rowbkp;
					for (; rowcnt < 4; i++) {
						int clmcnt = 0;
						j = clmbkp;
						for (; clmcnt < 4; j++) {
							tmp[rowcnt][clmcnt] = data[i][j];
							clmcnt++;
						}
						rowcnt++;
					}
					clmbkp = j;

					for (int x = 0; x < 4; x++) {
						for (int b = 0; b < 4; b++) {
							test++;
							
							writer.write(tmp[x][b] + " ");
							if (test == list.size()) {
								writer.write("\r\n");
								//test = 0;
							}
							System.out.print(tmp[x][b] + " ");
						}
						System.out.println();
						if(test==512){
							break;
						}
					}
					System.out.println();
					
				}
				rowbkp = i;
				clmbkp = 0;
				k = 0;
				cnt--;
			
			} while (cnt > 0);

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}