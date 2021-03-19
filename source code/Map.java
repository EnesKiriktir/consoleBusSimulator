package proje2;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class Map {
	public void loadMap(String[][] map) throws IOException {
		enigma.console.Console cn = Enigma.getConsole("Map");
		cn.getTextWindow().setCursorPosition(0,0);
		FileReader fr = new FileReader("filename.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		

		int i = 0;
		while ((line = br.readLine()) != null) {
			for (int j = 0; j < 58; j++) {
				map[i][j] = line.substring(j, j + 1);
			}
			i++;
		}
	}

	public void printMap(String[][] map,enigma.console.Console cn) {
	
		Enigma.getConsole("Map", 80, 30, 12, 0);
		cn.getTextWindow().setCursorPosition(0,3);
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 58; j++) {
				cn.setTextAttributes(new TextAttributes(Color.CYAN));
				String a = map[i][j];
				if(a.equals("#")) {
					cn.setTextAttributes(new TextAttributes(Color.RED));
					System.out.print(map[i][j]);
				}
				else {System.out.print(map[i][j]);}
				
			}
			System.out.println();
		}
	}

	public void clearScreen() {
		System.out.flush();
	}

}
