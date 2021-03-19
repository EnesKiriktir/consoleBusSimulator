package proje2;
import java.util.Timer;
import java.util.TimerTask;

import enigma.core.Enigma;

public class Time {

	static int secpassed = 0;
	static Timer myTime = new Timer();
	static TimerTask Task = new TimerTask()
	{
		public void run() {
			secpassed++;
			enigma.console.Console cn = Enigma.getConsole("Map");
			cn.getTextWindow().setCursorPosition(65, 0);
			System.out.println("Time: " + secpassed);
		}
	};

	static void Time() {
		myTime.scheduleAtFixedRate(Task, 1000, 1000);
	}

	

}
