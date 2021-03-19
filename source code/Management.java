package proje2;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.Scanner;

import enigma.console.Console;
import enigma.core.Enigma;

public class Management {
	Map m = new Map();
	String[][] map = new String[22][58]; // map arrayi

	static boolean stop = false;
	static int wait=3; // durak ve otobüs bilgilerinin ekranda durmasýnda geçenn süreyi ayarlamak için tutulan süre.

	public Management() throws IOException, InterruptedException {
		run();

	}
	static void writeInfoBusStop(BusStop stop)  { // duraklarýn bilgisini ekrana
		int counter =0;
		enigma.console.Console cn = Enigma.getConsole("Map");
		cn.getTextWindow().setCursorPosition(60, 14);
		System.out.print("Bus Stop " + "[" + stop.getName() + "] " + "Passengers:");
		cn.getTextWindow().setCursorPosition(60, 15);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 16);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 17);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 18);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 19);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 20);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 21);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 22);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 23);
		System.out.print("																		");cn.getTextWindow().setCursorPosition(60, 24);
		cn.getTextWindow().setCursorPosition(60, 15);
		for(int i=0; i<stop.passenger.length;i++) {
		
			if(stop.passenger[i]!=null) {
				if(stop.passenger[i].getLuggageCounter() == 2) {
					System.out.print(stop.passenger[i].passengerId +": "+stop.passenger[i].getBaslangýcDurak() + "-" + stop.passenger[i].getSonDurak() + "(L:"+stop.passenger[i].getLuggageId() +","+stop.passenger[i].getLuggageId2()+ ")");
				}
				else if(stop.passenger[i].getLuggageCounter() == 1) {
					System.out.print(stop.passenger[i].passengerId +": "+stop.passenger[i].getBaslangýcDurak() + "-" + stop.passenger[i].getSonDurak() + "(L:"+stop.passenger[i].getLuggageId() + ")");
				}
				else if(stop.passenger[i].getLuggageCounter() == 0) {
					System.out.print(stop.passenger[i].passengerId +": "+stop.passenger[i].getBaslangýcDurak() + "-" + stop.passenger[i].getSonDurak() + "(L:-)");
				}
				counter++;
				cn.getTextWindow().setCursorPosition(60, 15+counter);
			}
			
			}
		}
	
	
	
		
	static boolean kontrol(BusLine busline, BusStop[] lineStations, BusStop stop, Bus bus) { // duraða gelip gelmeme durumunu kontrol edip ona göre yolcu indir bindirlerini yapýyor.
		boolean flag = false;
		int counter=0;
		if (busline.getCoor_x() == stop.getCoor_x() && busline.getCoor_y() == stop.getCoor_y()) { // otobüs duraða geldimi diye kontrol ediyor.
			for (int i = 0; i < lineStations.length; i++) {
				if(lineStations[i].getName().equals(stop.getName())) {
					break;
				}
				
				counter++;
			}
			
			for (int i =counter; i < lineStations.length; i++) {
				
				for (int j = 0; j < stop.passenger.length; j++) {
					if(stop.passenger[j]!= null) {
						
					
					if (lineStations[i].getName().equals(stop.passenger[j].getSonDurak().toString())) {
						flag = true;
						 bus.addPassengerToBus(stop,j,bus);
						
					}
					}

				}
			}

		}
		for (int i = 0; i < 8; i++) {
			if(bus.passenger[i]!=null) {
			if(busline.getCoor_x() == stop.getCoor_x() && busline.getCoor_y() == stop.getCoor_y() && stop.getName().equals(bus.passenger[i].getSonDurak().toString())) {
				bus.removeFromTheBus(bus.passenger[i],bus);
			}
		}
		}
		
		return flag;
	}
	public void run() throws IOException, InterruptedException {
		enigma.console.Console cn = Enigma.getConsole("Map");
		//geerekli stackler yaratýldý.
		Stack s = new Stack(1000);
		Stack lineTempA = new Stack(1000); // A2 otobüsünün güzergahý
		Stack lineA = new Stack(1000); //  A1 otobüsünün güzergahý
		Stack bosA = new Stack(1000); // A1 n bos stack i
		Stack bosTempA = new Stack(1000); // A2 nin boþ stack i
		Stack lineTempB = new Stack(1000);
		Stack lineB = new Stack(1000);
		Stack bosB = new Stack(1000);
		Stack bosTempB = new Stack(1000);
		Stack lineTempC = new Stack(1000);
		Stack lineC = new Stack(1000);
		Stack bosC = new Stack(1000);
		Stack bosTempC = new Stack(1000);
		Stack lineD = new Stack(1000);
		Stack lineTempD = new Stack(1000);
		Stack bosD = new Stack(1000);
		Stack bosTempD = new Stack(1000);
		Stack lineTempL = new Stack(1000);
		Stack lineL = new Stack(1000);
		Stack bosL = new Stack(1000);
		Stack bosTempL = new Stack(1000);
		Stack lineTempM = new Stack(1000);
		Stack lineM = new Stack(1000);
		Stack bosM = new Stack(1000);
		Stack bosTempM = new Stack(1000);
		// yolcu kuyruðu yaratma
		Queue passengerQueue = new Queue(5000);
		int[] searchForSameId = new int[1000];
		// line larýn duraklarý array içerisine hareket fonksiyonunda karþýlaþtýrmak
		// amaçlý kullanmak için yaratýldý.
		BusStop[] lineAStations = new BusStop[6];
		BusStop[] lineA2Stations = new BusStop[6];
		BusStop[] lineBStations = new BusStop[7];
		BusStop[] lineB2Stations = new BusStop[7];
		BusStop[] lineCStations = new BusStop[7];
		BusStop[] lineC2Stations = new BusStop[7];
		BusStop[] lineDStations = new BusStop[7];
		BusStop[] lineD2Stations = new BusStop[7];
		BusStop[] lineLStations = new BusStop[8];
		BusStop[] lineL2Stations = new BusStop[8];
		BusStop[] lineMStations = new BusStop[8];
		BusStop[] lineM2Stations = new BusStop[8];
		BusStop[] line = null; //control fonksiyonundan gelen arrayi tutar.
		BusStop[] line2 = null; // control2 fonksiyonundan gelen array i tutar.
		Passenger passenger = new Passenger(' ', ' ', 0);
		Passenger passenger2 = new Passenger(' ', ' ', 0, 0);
		Passenger passenger3 = new Passenger(' ', ' ', 0, 0);

		
		
		
		//
		
		//
		
		
		// Duraklarý yaratma.
		BusStop a = new BusStop("A", 2, 2);
		BusStop b = new BusStop("B", 2, 14);
		BusStop c = new BusStop("C", 2, 32);
		BusStop d = new BusStop("D", 2, 50);
		BusStop e = new BusStop("E", 8, 2);
		BusStop f = new BusStop("F", 8, 14);
		BusStop g = new BusStop("G", 8, 32);
		BusStop h = new BusStop("H", 8, 50);
		BusStop ý = new BusStop("I", 14, 2);
		BusStop j = new BusStop("J", 14, 14);
		BusStop k = new BusStop("K", 14, 32);
		BusStop l = new BusStop("L", 14, 44);
		BusStop md = new BusStop("M", 20, 8);
		BusStop n = new BusStop("N", 20, 14);
		BusStop o = new BusStop("O", 20, 32);
		BusStop p = new BusStop("P", 20, 44);
		// addNullToBusStop fonksiyonuyla duraklarýn yolcu arraylerine null
		// atýlýyor(BusStop satýr 15-19) daha sonrada duraktaki yolcu sayýsýný saymak
		// için passenger[i]!=null þeklinde if kullanýlýyor(
		a.addNullToBusStop();
		b.addNullToBusStop();
		c.addNullToBusStop();
		d.addNullToBusStop();
		e.addNullToBusStop();
		f.addNullToBusStop();
		g.addNullToBusStop();
		h.addNullToBusStop();
		ý.addNullToBusStop();
		j.addNullToBusStop();
		k.addNullToBusStop();
		l.addNullToBusStop();
		md.addNullToBusStop();
		n.addNullToBusStop();
		o.addNullToBusStop();
		p.addNullToBusStop();

		// otobüslerin yaratýlmasý
		Bus A1 = new Bus("A1");
		Bus A2 = new Bus("A2");
		Bus B1 = new Bus("B1");
		Bus B2 = new Bus("B2");
		Bus C1 = new Bus("C1");
		Bus C2 = new Bus("C2");
		Bus D1 = new Bus("D1");
		Bus D2 = new Bus("D2");
		Bus L1 = new Bus("L1");
		Bus L2 = new Bus("L2");
		Bus M1 = new Bus("M1");
		Bus M2 = new Bus("M2");

		// duraklarý line lara göre array lere atma.
		lineAStations[0] = a  ;lineAStations[1] = e  ;lineAStations[2] = ý;lineAStations[3] = j;lineAStations[4] = k;lineAStations[5] = l;
		lineA2Stations[0] = l ;lineA2Stations[1] = k ;lineA2Stations[2] = j;lineA2Stations[3] = ý;lineA2Stations[4] = e;lineA2Stations[5] = a;
		lineBStations[0] = b  ;lineBStations[1] = f  ;lineBStations[2] = e;lineBStations[3] = ý;lineBStations[4] = j;lineBStations[5] = n;lineBStations[6] = md;
		lineB2Stations[0] = md;lineB2Stations[1] = n ;lineB2Stations[2] = j;lineB2Stations[3] = ý;lineB2Stations[4] = e;lineB2Stations[5] = f;lineB2Stations[6] = b;
		lineCStations[0] = c  ;lineCStations[1] = g  ;lineCStations[2] = k;lineCStations[3] = j;lineCStations[4] = f;lineCStations[5] = e;lineCStations[6] = a;
		lineC2Stations[0] = a ;lineC2Stations[1] = e ;lineC2Stations[2] = f;lineC2Stations[3] = j;lineC2Stations[4] = k;lineC2Stations[5] = g;lineC2Stations[6] = c;
		lineDStations[0] = d  ;lineDStations[1] = c  ;lineDStations[2] = g;lineDStations[3] = k;lineDStations[4] = j;lineDStations[5] = n;lineDStations[6] = md;
		lineD2Stations[0] = md;lineD2Stations[1] = n ;lineD2Stations[2] = j;lineD2Stations[3] = k;lineD2Stations[4] = g;lineD2Stations[5] = c;lineD2Stations[6] = d;
		lineLStations[0] = l  ;lineLStations[1] = p  ;lineLStations[2] = o;lineLStations[3] = k;lineLStations[4] = g;lineLStations[5] = c;lineLStations[6] = d;lineLStations[7] = h;
		lineL2Stations[0] = h ;lineL2Stations[1] = d ;lineL2Stations[2] = c;lineL2Stations[3] = g;lineL2Stations[4] = k;lineL2Stations[5] = o;lineL2Stations[6] = p;lineL2Stations[7] = l;
		lineMStations[0] = md ;lineMStations[1] = n  ;lineMStations[2] = j;lineMStations[3] = f;lineMStations[4] = g;lineMStations[5] = k;lineMStations[6] = o;lineMStations[7] = p;
		lineM2Stations[0] = p ;lineM2Stations[1] = o ;lineM2Stations[2] = k;lineM2Stations[3] = g;lineM2Stations[4] = f;lineM2Stations[5] = j;lineM2Stations[6] = n;lineM2Stations[7] = md;
		for (int i = 0; i < 7; i++) { // durak A dan durak E ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2 + i, 2);
			lineTempA.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak I ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 + i, 2);
			lineTempA.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak J ye kadar oolan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 2 + i);
			lineTempA.push(bus);
		}

		for (int i = 1; i < 19; i++) { // durak K ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 14 + i);
			lineTempA.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak L ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 32 + i);
			lineTempA.push(bus);
		}

		while (!lineTempA.isEmpty()) {
			lineA.push(lineTempA.peek());
			s.push(lineTempA.pop());
		}
		while (!s.isEmpty()) {
			lineTempA.push(s.pop());
		}

		for (int i = 0; i < 7; i++) { // durak B den durak F ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2 + i, 14);
			lineTempB.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak E ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8, 14 - i);
			lineTempB.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak I ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 + i, 2);
			lineTempB.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak J ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 2 + i);
			lineTempB.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak N ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 + i, 14);
			lineTempB.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak M ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20, 14 - i);
			lineTempB.push(bus);
		}

		while (!lineTempB.isEmpty()) {
			lineB.push(lineTempB.peek());
			s.push(lineTempB.pop());
		}
		while (!s.isEmpty()) {
			lineTempB.push(s.pop());
		}

		for (int i = 0; i < 7; i++) { // durak C den durak G ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2 + i, 32);
			lineTempC.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak K ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 + i, 32);
			lineTempC.push(bus);
		}

		for (int i = 1; i < 19; i++) { // durak J ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 32 - i);
			lineTempC.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak F ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 - i, 14);
			lineTempC.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak E ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8, 14 - i);
			lineTempC.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak A ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 - i, 2);
			lineTempC.push(bus);
		}

		while (!lineTempC.isEmpty()) {
			lineC.push(lineTempC.peek());
			s.push(lineTempC.pop());
		}
		while (!s.isEmpty()) {
			lineTempC.push(s.pop());
		}

		for (int i = 0; i < 19; i++) { // durak D den durak C ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2, 50 - i);
			lineTempD.push(bus);
		}
		for (int i = 1; i < 7; i++) { // durak G ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2 + i, 32);
			lineTempD.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak K ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 + i, 32);
			lineTempD.push(bus);
		}
		for (int i = 1; i < 19; i++) { // durak J ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14, 32 - i);
			lineTempD.push(bus);
		}
		for (int i = 1; i < 7; i++) { // durak N ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 + i, 14);
			lineTempD.push(bus);
		}
		for (int i = 1; i < 7; i++) { // durak M ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20, 14 - i);
			lineTempD.push(bus);
		}

		while (!lineTempD.isEmpty()) {
			lineD.push(lineTempD.peek());
			s.push(lineTempD.pop());
		}
		while (!s.isEmpty()) {
			lineTempD.push(s.pop());
		}

		for (int i = 0; i < 7; i++) { // durak L den durak P ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 + i, 44);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 13; i++) { // durak O ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20, 44 - i);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak K ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20 - i, 32);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak G ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 - i, 32);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak C yee kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 - i, 32);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 19; i++) { // durak D ye kadar oolan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2, 32 + i);
			lineTempL.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak H ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(2 + i, 50);
			lineTempL.push(bus);
		}

		while (!lineTempL.isEmpty()) {
			lineL.push(lineTempL.peek());
			s.push(lineTempL.pop());
		}
		while (!s.isEmpty()) {
			lineTempL.push(s.pop());
		}

		for (int i = 0; i < 7; i++) { // durak M den durak N ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20, 8 + i);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak J ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(20 - i, 14);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak F ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 - i, 14);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 19; i++) { // durak G ye kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8, 14 + i);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak K ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(8 + i, 32);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 7; i++) { // durak O ya kadar olan yolun koordinatlarý stack içine atýldý.
			BusLine bus = new BusLine(14 + i, 32);
			lineTempM.push(bus);
		}

		for (int i = 1; i < 13; i++) {
			BusLine bus = new BusLine(20, 32 + i);
			lineTempM.push(bus);
		}

		while (!lineTempM.isEmpty()) {
			lineM.push(lineTempM.peek());
			s.push(lineTempM.pop());
		}
		while (!s.isEmpty()) {
			lineTempM.push(s.pop());
		}

		// ConsoleReader r = new ConsoleReader();
		boolean flag = true;
		// while da kullanýlmak üzere BusLine tipinde koordinatlarý gecici olarak
		// tutacaðýmýz nesneler oluþturuldu.
		BusLine moveA = new BusLine(0, 0);
		BusLine moveA2 = new BusLine(0, 0);
		BusLine moveB = new BusLine(0, 0);
		BusLine moveB2 = new BusLine(0, 0);
		BusLine moveC = new BusLine(0, 0);
		BusLine moveC2 = new BusLine(0, 0);
		BusLine moveD = new BusLine(0, 0);
		BusLine moveD2 = new BusLine(0, 0);
		BusLine moveL = new BusLine(0, 0);
		BusLine moveL2 = new BusLine(0, 0);
		BusLine moveM = new BusLine(0, 0);
		BusLine moveM2 = new BusLine(0, 0);
		//bir önceki koordinatlarý tutmak için yaratýldýlar.
		BusLine previousA1 = new BusLine(0, 0);
		BusLine previousA2 = new BusLine(0, 0);
		BusLine previousB1 = new BusLine(0, 0);
		BusLine previousB2 = new BusLine(0, 0);
		BusLine previousC1 = new BusLine(0, 0);
		BusLine previousC2 = new BusLine(0, 0);
		BusLine previousD1 = new BusLine(0, 0);
		BusLine previousD2 = new BusLine(0, 0);
		BusLine previousL1 = new BusLine(0, 0);
		BusLine previousL2 = new BusLine(0, 0);
		BusLine previousM1 = new BusLine(0, 0);
		BusLine previousM2 = new BusLine(0, 0);
		Time.Time();// time ý while içerisine alýnca program hata veriyor.

		m.loadMap(map);

		boolean a1ileri = true;
		boolean a1geri = false;
		boolean a2ileri = true;
		boolean a2geri = false;
		boolean b1ileri = true;
		boolean b1geri = false;
		boolean b2ileri = true;
		boolean b2geri = false;
		boolean c1ileri = true;
		boolean c1geri = false;
		boolean c2ileri = true;
		boolean c2geri = false;
		boolean d1ileri = true;
		boolean d1geri = false;
		boolean d2ileri = true;
		boolean d2geri = false;
		boolean l1ileri = true;
		boolean l1geri = false;
		boolean l2ileri = true;
		boolean l2geri = false;
		boolean m1ileri = true;
		boolean m1geri = false;
		boolean m2ileri = true;
		boolean m2geri = false;

		
		
		Queue tempPassengerQueue = new Queue(5000);
		Queue tempPassengerQueue2 = new Queue(5000);
		
		int belirle=0;
		int belirleDurakOtobüs =0;
		while (flag = true) {
			Random r = new Random();
			//bustop bilgileri ekrana yazdýrýldý.
			if(wait==3) {
			belirle = r.nextInt(16);
			belirleDurakOtobüs = 0;
			if(belirleDurakOtobüs ==0) {
				if(belirle==0) {writeInfoBusStop(a);}
				else if(belirle==1) {writeInfoBusStop(b);}
				else if(belirle==2) {writeInfoBusStop(c);}
				else if(belirle==3) {writeInfoBusStop(d);}
				else if(belirle==4) {writeInfoBusStop(e);}
				else if(belirle==5) {writeInfoBusStop(f);}
				else if(belirle==6) {writeInfoBusStop(g);}
				else if(belirle==7) {writeInfoBusStop(h);}
				else if(belirle==8) {writeInfoBusStop(ý);}
				else if(belirle==9) {writeInfoBusStop(j);}
				else if(belirle==10) {writeInfoBusStop(k);}
				else if(belirle==11) {writeInfoBusStop(l);}
				else if(belirle==12) {writeInfoBusStop(md);}
				else if(belirle==13) {writeInfoBusStop(n);}
				else if(belirle==14) {writeInfoBusStop(o);}
				else if(belirle==15) {writeInfoBusStop(p);}
				wait--;
			}
			}
			else{
				wait--;
				if(wait==0) {
					wait=3;
			}
			
			}
			Passenger pass = null;
			Character baslangýcDurak =' ' ; // yolcunun hangi duraktan bineceði tutuluyor.
			Character sonDurak=' '; // yolcunun hangi durakta ineceði tutuluyor.
			int durakyolcu=0; // duraklardaki toplam yolcu sayýsýný tutuyor.
			durakyolcu=a.passengercounter() + b.passengercounter() + c.passengercounter()+ d.passengercounter()+ e.passengercounter()+ f.passengercounter()
			+ g.passengercounter()+ h.passengercounter()+ ý.passengercounter()+ j.passengercounter()+ k.passengercounter()+ l.passengercounter()+ 
			md.passengercounter()+ n.passengercounter()+ o.passengercounter()+ p.passengercounter();
			
			int otobüsyolcu=0; // otobüslerde ne kadar yolcu olduðu tutuluyor.
			otobüsyolcu = A1.passengerNumber()+A2.passengerNumber()+B1.passengerNumber()+B2.passengerNumber()+C1.passengerNumber()+C2.passengerNumber()+D1.passengerNumber()
			+D2.passengerNumber()+L1.passengerNumber()+L2.passengerNumber()+M1.passengerNumber()+M2.passengerNumber();
			
			int fullness=0;//  otobülerdeki toplam doluluk oranýný veriyor
			fullness=(100/96)*otobüsyolcu;
			cn.getTextWindow().setCursorPosition(60, 10);
			System.out.print("Waiting: "+durakyolcu);
			cn.getTextWindow().setCursorPosition(60, 11);
			System.out.print("Travelling: "+otobüsyolcu);
			cn.getTextWindow().setCursorPosition(60, 12);
			System.out.print("Bus Fullness: %"+fullness);
			
			
			
			
			if (passengerQueue.size() <= 15) {
				Random rnd = new Random();
				int passengerId = rnd.nextInt(1000); // yolcu id'sini random olarak atýyor.
				int numberOfLuggage = rnd.nextInt(3);// yolcunun kaç bagajý olacaðýný tutuyor.
				int luggageId = rnd.nextInt(999)+1; // ilk bagajýn id'si.
				int luggageId2 = rnd.nextInt(999)+1; // ikinci bagajýn id'si.
				int random = rnd.nextInt(6);  // yolcunun hangi line da yolculuk yapacaðý tutuluyor.
				if(random==0) { // line A da yolculuk yapýyor.
					int rB=0;// yolcunun baþlangýc duraðýnýn random deðeri
					int rS=0;// yolcunun bitiþ duraðýnýn random deðeri 
					do {
						rB = rnd.nextInt(6);
						rS = rnd.nextInt(6);
					}while(rB==rS);
					
					baslangýcDurak=(lineAStations[rB].getName().charAt(0));
					sonDurak =(lineAStations[rS].getName().charAt(0));
					
				}
				else if(random==1) {
					int rB=0;
					int rS=0;
					do {
						rB = rnd.nextInt(7);
						rS = rnd.nextInt(7);
					}while(rB==rS);
					
					baslangýcDurak=(lineBStations[rB].getName().charAt(0));
					sonDurak =(lineBStations[rS].getName().charAt(0));
					
				}
				else if(random==2) {
					int rB=0;
					int rS=0;
					do {
						rB = rnd.nextInt(7);
						rS = rnd.nextInt(7);
					}while(rB==rS);
					
					baslangýcDurak=(lineCStations[rB].getName().charAt(0));
					sonDurak =(lineCStations[rS].getName().charAt(0));
					
				}
				else if(random==3) {
					int rB=0;
					int rS=0;
					do {
						rB = rnd.nextInt(7);
						rS = rnd.nextInt(7);
					}while(rB==rS);
					
					baslangýcDurak=(lineDStations[rB].getName().charAt(0));
					sonDurak =(lineDStations[rS].getName().charAt(0));
					
				}
				else if(random==4) {
					int rB=0;
					int rS=0;
					do {
						rB = rnd.nextInt(8);
						rS = rnd.nextInt(8);
					}while(rB==rS);
					
					baslangýcDurak=(lineLStations[rB].getName().charAt(0));
					sonDurak =(lineLStations[rS].getName().charAt(0));
					
				}
				else if(random==5) {
					int rB=0;
					int rS=0;
					do {
						rB = rnd.nextInt(8);
						rS = rnd.nextInt(8);
					}while(rB==rS);
					
					baslangýcDurak=(lineMStations[rB].getName().charAt(0));
					sonDurak =(lineMStations[rS].getName().charAt(0));
					
				}
					
				
				Character basDurak = baslangýcDurak;
				Character sonDrk = sonDurak; 

				// luggage sayýsýna göre constructor yapýldý.
				if (numberOfLuggage == 0) {
					passenger = new Passenger(basDurak, sonDrk, passengerId);
					pass = passenger;
					pass.setLuggageCounter(0);
				} else if (numberOfLuggage == 1) {
					passenger2 = new Passenger(basDurak, sonDrk, passengerId, luggageId);
					pass = passenger2;
					pass.setLuggageCounter(1);
				} else if (numberOfLuggage == 2) {
					passenger3 = new Passenger(basDurak, sonDrk, passengerId, luggageId, luggageId2);
					pass = passenger3;
					pass.setLuggageCounter(2);
				}
				passengerQueue.enqueue(pass);
				
				String bD = basDurak.toString();// yolcunun hangi duraktan bineceðini bulup onu o duraða yerleþtirmek için kullanýlýyor.
				if (bD.equals("A")) {a.addPassengerToBusStop(pass);} 
				else if (bD.equals("B")) {b.addPassengerToBusStop(pass);} 
				else if (bD.equals("C")) {c.addPassengerToBusStop(pass);}
				else if (bD.equals("D")) {d.addPassengerToBusStop(pass);} 
				else if (bD.equals("E")) {e.addPassengerToBusStop(pass);} 
				else if (bD.equals("F")) {f.addPassengerToBusStop(pass);}
				else if (bD.equals("G")) {g.addPassengerToBusStop(pass);} 
				else if (bD.equals("H")) {h.addPassengerToBusStop(pass);} 
				else if (bD.equals("I")) {ý.addPassengerToBusStop(pass);} 
				else if (bD.equals("J")) {j.addPassengerToBusStop(pass);} 
				else if (bD.equals("K")) {k.addPassengerToBusStop(pass);} 
				else if (bD.equals("L")) {l.addPassengerToBusStop(pass);} 
				else if (bD.equals("M")) {md.addPassengerToBusStop(pass);}
				else if (bD.equals("N")) {n.addPassengerToBusStop(pass);} 
				else if (bD.equals("O")) {o.addPassengerToBusStop(pass);} 
				else if (bD.equals("P")) {p.addPassengerToBusStop(pass);}
				
				//yolcu queue sunu ekrana yazdýrýyor
				cn.getTextWindow().setCursorPosition(60, 6);
				System.out.print("<<<New Passegers<<<");
				cn.getTextWindow().setCursorPosition(62,7);
				System.out.print("---------------");
				cn.getTextWindow().setCursorPosition(62,9);
				System.out.print("---------------");
				
				cn.getTextWindow().setCursorPosition(62,8);
				if(tempPassengerQueue.size()<15) {
					tempPassengerQueue.enqueue(passengerQueue.peek());
				}
				else {
					tempPassengerQueue.dequeue();
					tempPassengerQueue.enqueue(passengerQueue.peek());
				}
				while(!tempPassengerQueue.isEmpty()) {
					Passenger passs = (Passenger)tempPassengerQueue.peek();
					tempPassengerQueue2.enqueue(tempPassengerQueue.dequeue());
					System.out.print(passs.getBaslangýcDurak());
				}
				while(!tempPassengerQueue2.isEmpty()){
					tempPassengerQueue.enqueue(tempPassengerQueue2.dequeue());
				}
				
				passengerQueue.dequeue();
				
			}
			
			//stacklerden BusLine tipindeki koordinatlarý çekerek bir öncesinin koordinatlarýný tutmuþ oluyor.

			if (!bosA.isEmpty()) // ileri gitme
			{
				previousA1 = (BusLine) bosA.peek();
			}

			if (a1geri) // geri gitme
			{
				if (lineA.peek() != null) {
					previousA1 = (BusLine) lineA.peek();
				}
			}

			if (!bosTempA.isEmpty()) // ileri gitme
			{
				previousA2 = (BusLine) bosTempA.peek();
			}

			if (a2geri) // geri gitme
			{
				if (lineTempA.peek() != null) {
					previousA2 = (BusLine) lineTempA.peek();
				}
			}

			if (!bosB.isEmpty()) {
				previousB1 = (BusLine) bosB.peek();
			}

			if (b1geri) {
				if (lineB.peek() != null) {
					previousB1 = (BusLine) lineB.peek();
				}
			}

			if (!bosTempB.isEmpty()) {
				previousB2 = (BusLine) bosTempB.peek();
			}

			if (b2geri) {
				if (lineTempB.peek() != null) {
					previousB2 = (BusLine) lineTempB.peek();
				}
			}

			if (!bosC.isEmpty()) {
				previousC1 = (BusLine) bosC.peek();
			}

			if (c1geri) {
				if (lineC.peek() != null) {
					previousC1 = (BusLine) lineC.peek();
				}
			}

			if (!bosTempC.isEmpty()) {
				previousC2 = (BusLine) bosTempC.peek();
			}

			if (c2geri) {
				if (lineTempC.peek() != null) {
					previousC2 = (BusLine) lineTempC.peek();
				}
			}

			if (!bosD.isEmpty()) {
				previousD1 = (BusLine) bosD.peek();
			}

			if (d1geri) {
				if (lineD.peek() != null) {
					previousD1 = (BusLine) lineD.peek();
				}
			}

			if (!bosTempD.isEmpty()) {
				previousD2 = (BusLine) bosTempD.peek();
			}

			if (d2geri) {
				if (lineTempD.peek() != null) {
					previousD2 = (BusLine) lineTempD.peek();
				}
			}

			if (!bosL.isEmpty()) {
				previousL1 = (BusLine) bosL.peek();
			}

			if (l1geri) {
				if (lineL.peek() != null) {
					previousL1 = (BusLine) lineL.peek();
				}
			}

			if (!bosTempL.isEmpty()) {
				previousL2 = (BusLine) bosTempL.peek();
			}

			if (l2geri) {
				if (lineTempL.peek() != null) {
					previousL2 = (BusLine) lineTempL.peek();
				}
			}

			if (!bosM.isEmpty()) {
				previousM1 = (BusLine) bosM.peek();
			}

			if (m1geri) {
				if (lineM.peek() != null) {
					previousM1 = (BusLine) lineM.peek();
				}
			}

			if (!bosTempM.isEmpty()) {
				previousM2 = (BusLine) bosTempM.peek();
			}

			if (m2geri) {
				if (lineTempM.peek() != null) {
					previousM2 = (BusLine) lineTempM.peek();
				}
			}

			m.clearScreen();

			// lineA lineB denilen stacklerde her güzergahýn 1 nolu otobüsünün koordinatlarý
			// bulunmakta(A1,B1... gibi).
			if (a1ileri) {
				
				
				kontrol(moveA,lineAStations,a,A1);//
				kontrol(moveA,lineAStations,e,A1);
				kontrol(moveA,lineAStations,ý,A1);
				kontrol(moveA,lineAStations,j,A1);
				kontrol(moveA,lineAStations,k,A1);
				kontrol(moveA,lineAStations,l,A1);
				//
				
				if(A1.waitTime==0) {
				moveA = (BusLine) lineA.peek();
				bosA.push(lineA.pop());
				
				}
				else {
					A1.waitTime--;
				}		
	
				
			}

			if (a1geri) {
				//kontrolle otobüs güzergahý üzerindeki herhangi bir duraða gelmiþ mi diye kontrol ediyor.
				kontrol(moveA,lineA2Stations,l,A1);
				kontrol(moveA,lineA2Stations,k,A1);
				kontrol(moveA,lineA2Stations,j,A1);
				kontrol(moveA,lineA2Stations,ý,A1);
				kontrol(moveA,lineA2Stations,e,A1);
				kontrol(moveA,lineA2Stations,a,A1);
				
				if(A1.waitTime==0) { //luggage operasyonlarý gecikme yapýlýyor. 
					moveA = (BusLine) bosA.peek();
					lineA.push(bosA.pop());
				}
				else {
					A1.waitTime--;
				}

			}

			
			if (lineA.size() == 0) {
				a1geri = true;
				a1ileri = false;
			}
			if (bosA.size() == 0) {
				a1geri = false;
				a1ileri = true;
			}

			// --
			if (a2ileri) {
				
				kontrol(moveA2,lineA2Stations,l,A2);
				kontrol(moveA2,lineA2Stations,k,A2);
				kontrol(moveA2,lineA2Stations,j,A2);
				kontrol(moveA2,lineA2Stations,ý,A2);
				kontrol(moveA2,lineA2Stations,e,A2);
				kontrol(moveA2,lineA2Stations,a,A2);
				
				if(A2.waitTime==0) {
				moveA2 = (BusLine) lineTempA.peek();
				bosTempA.push(lineTempA.pop());
				}
				else {
					A2.waitTime--;
				}
			}

			if (a2geri) {
				
				kontrol(moveA2,lineAStations,a,A2);
				kontrol(moveA2,lineAStations,e,A2);
				kontrol(moveA2,lineAStations,ý,A2);
				kontrol(moveA2,lineAStations,j,A2);
				kontrol(moveA2,lineAStations,k,A2);
				kontrol(moveA2,lineAStations,l,A2);
				
				if(A2.waitTime==0) {
				moveA2 = (BusLine) bosTempA.peek();
				lineTempA.push(bosTempA.pop());
				}
				else {
					A2.waitTime--;
				}
			}

			if (lineTempA.size() == 0) {
				a2geri = true;
				a2ileri = false;
			}
			if (bosTempA.size() == 0) {
				a2geri = false;
				a2ileri = true;
			}
			// --

			if (b1ileri) {
				
				kontrol(moveB,lineBStations,b,B1);
				kontrol(moveB,lineBStations,f,B1);
				kontrol(moveB,lineBStations,e,B1);
				kontrol(moveB,lineBStations,ý,B1);
				kontrol(moveB,lineBStations,j,B1);
				kontrol(moveB,lineBStations,n,B1);
				kontrol(moveB,lineBStations,md,B1);
				
				if(B1.waitTime==0) {
				moveB = (BusLine) lineB.peek();
				bosB.push(lineB.pop());
				}
				else {
					B1.waitTime--;
				}
			}

			if (b1geri) {
				
				
				kontrol(moveB,lineB2Stations,md,B1);
				kontrol(moveB,lineB2Stations,n,B1);
				kontrol(moveB,lineB2Stations,j,B1);
				kontrol(moveB,lineB2Stations,ý,B1);
				kontrol(moveB,lineB2Stations,e,B1);
				kontrol(moveB,lineB2Stations,f,B1);
				kontrol(moveB,lineB2Stations,b,B1);
				
				if(B1.waitTime==0) {
				moveB = (BusLine) bosB.peek();
				lineB.push(bosB.pop());
				}
				else {
					B1.waitTime--;
				}
			}
			
			if (lineB.size() == 0) {
				b1geri = true;
				b1ileri = false;
			}
			if (bosB.size() == 0) {
				b1geri = false;
				b1ileri = true;
			}

			//
			if (b2ileri) {
				
				kontrol(moveB2,lineB2Stations,md,B2);
				kontrol(moveB2,lineB2Stations,n,B2);
				kontrol(moveB2,lineB2Stations,j,B2);
				kontrol(moveB2,lineB2Stations,ý,B2);
				kontrol(moveB2,lineB2Stations,e,B2);
				kontrol(moveB2,lineB2Stations,f,B2);
				kontrol(moveB2,lineB2Stations,b,B2);
				
				if(B2.waitTime==0) {
				moveB2 = (BusLine) lineTempB.peek();
				bosTempB.push(lineTempB.pop());
				}
				else {
					B2.waitTime--;
				}
		
			}

			if (b2geri) {
				
				kontrol(moveB2,lineBStations,b,B2);
				kontrol(moveB2,lineBStations,f,B2);
				kontrol(moveB2,lineBStations,e,B2);
				kontrol(moveB2,lineBStations,ý,B2);
				kontrol(moveB2,lineBStations,j,B2);
				kontrol(moveB2,lineBStations,n,B2);
				kontrol(moveB2,lineBStations,md,B2);
				if(B2.waitTime==0) {
				moveB2 = (BusLine) bosTempB.peek();
				lineTempB.push(bosTempB.pop());
				}
				else {
					B2.waitTime--;
				}
		
			}
			if (lineTempB.size() == 0) {
				b2geri = true;
				b2ileri = false;
			}
			if (bosTempB.size() == 0) {
				b2geri = false;
				b2ileri = true;
			}
			//
			if (c1ileri) {
				kontrol(moveC,lineCStations,c,C1);
				kontrol(moveC,lineCStations,g,C1);
				kontrol(moveC,lineCStations,k,C1);
				kontrol(moveC,lineCStations,j,C1);
				kontrol(moveC,lineCStations,f,C1);
				kontrol(moveC,lineCStations,e,C1);
				kontrol(moveC,lineCStations,a,C1);
				if(C1.waitTime==0) {
				moveC = (BusLine) lineC.peek();
				bosC.push(lineC.pop());
				}
				else {
					C1.waitTime--;
				}
			
			}
			if (c1geri) {
				
				kontrol(moveC,lineC2Stations,a,C1);
				kontrol(moveC,lineC2Stations,e,C1);
				kontrol(moveC,lineC2Stations,f,C1);
				kontrol(moveC,lineC2Stations,j,C1);
				kontrol(moveC,lineC2Stations,k,C1);
				kontrol(moveC,lineC2Stations,g,C1);
				kontrol(moveC,lineC2Stations,c,C1);
				if(C1.waitTime==0) {
				moveC = (BusLine) bosC.peek();
				lineC.push(bosC.pop());
				}
				else {
					C1.waitTime--;
				}
				
			}
			if (lineC.size() == 0) {
				c1geri = true;
				c1ileri = false;
			}
			if (bosC.size() == 0) {
				c1geri = false;
				c1ileri = true;
			}

			//
			if (c2ileri) {
				
				kontrol(moveC2,lineC2Stations,a,C2);
				kontrol(moveC2,lineC2Stations,e,C2);
				kontrol(moveC2,lineC2Stations,f,C2);
				kontrol(moveC2,lineC2Stations,j,C2);
				kontrol(moveC2,lineC2Stations,k,C2);
				kontrol(moveC2,lineC2Stations,g,C2);
				kontrol(moveC2,lineC2Stations,c,C2);
				if(C2.waitTime==0) {
				moveC2 = (BusLine) lineTempC.peek();
				bosTempC.push(lineTempC.pop());
				}
				else {
					C2.waitTime--;
				}
			
			}
			if (c2geri) {
				
				kontrol(moveC2,lineCStations,c,C2);
				kontrol(moveC2,lineCStations,g,C2);
				kontrol(moveC2,lineCStations,k,C2);
				kontrol(moveC2,lineCStations,j,C2);
				kontrol(moveC2,lineCStations,f,C2);
				kontrol(moveC2,lineCStations,e,C2);
				kontrol(moveC2,lineCStations,a,C2);
				if(C2.waitTime==0) {
				moveC2 = (BusLine) bosTempC.peek();
				lineTempC.push(bosTempC.pop());
				}
				else {
					C2.waitTime--;
				}
		
			}
			if (lineTempC.size() == 0) {
				c2geri = true;
				c2ileri = false;
			}
			if (bosTempC.size() == 0) {
				c2geri = false;
				c2ileri = true;
			}
			//

			if (d1ileri) {
				kontrol(moveD,lineDStations,d,D1);
				kontrol(moveD,lineDStations,c,D1);
				kontrol(moveD,lineDStations,g,D1);
				kontrol(moveD,lineDStations,k,D1);
				kontrol(moveD,lineDStations,j,D1);
				kontrol(moveD,lineDStations,n,D1);
				kontrol(moveD,lineDStations,md,D1);
				if(D1.waitTime==0) {
				moveD = (BusLine) lineD.peek();
				bosD.push(lineD.pop());
				}
				else {
					D1.waitTime--;
				}
			
			}
			if (d1geri) {
				
				kontrol(moveD,lineD2Stations,md,D1);
				kontrol(moveD,lineD2Stations,n,D1);
				kontrol(moveD,lineD2Stations,j,D1);
				kontrol(moveD,lineD2Stations,k,D1);
				kontrol(moveD,lineD2Stations,g,D1);
				kontrol(moveD,lineD2Stations,c,D1);
				kontrol(moveD,lineD2Stations,d,D1);
				if(D1.waitTime==0) {
				moveD = (BusLine) bosD.peek();
				lineD.push(bosD.pop());
				}
				else {
					D1.waitTime--;
				}
			
			}
			if (lineD.size() == 0) {
				d1geri = true;
				d1ileri = false;
			}
			if (bosD.size() == 0) {
				d1geri = false;
				d1ileri = true;
			}

			//
			if (d2ileri) {
				
				kontrol(moveD2,lineD2Stations,md,D2);
				kontrol(moveD2,lineD2Stations,n,D2);
				kontrol(moveD2,lineD2Stations,j,D2);
				kontrol(moveD2,lineD2Stations,k,D2);
				kontrol(moveD2,lineD2Stations,g,D2);
				kontrol(moveD2,lineD2Stations,c,D2);
				kontrol(moveD2,lineD2Stations,d,D2);
				
				if(D2.waitTime==0) {
				moveD2 = (BusLine) lineTempD.peek();
				bosTempD.push(lineTempD.pop());
				}
				else {
					D2.waitTime--;
				}
	

			}
			if (d2geri) {
				
				kontrol(moveD2,lineDStations,d,D2);
				kontrol(moveD2,lineDStations,c,D2);
				kontrol(moveD2,lineDStations,g,D2);
				kontrol(moveD2,lineDStations,k,D2);
				kontrol(moveD2,lineDStations,j,D2);
				kontrol(moveD2,lineDStations,n,D2);
				kontrol(moveD2,lineDStations,md,D2);
				
				if(D2.waitTime==0) {
				moveD2 = (BusLine) bosTempD.peek();
				lineTempD.push(bosTempD.pop());
				}
				else {
					D2.waitTime--;
				}
		
			}
			if (lineTempD.size() == 0) {
				d2geri = true;
				d2ileri = false;
			}
			if (bosTempD.size() == 0) {
				d2geri = false;
				d2ileri = true;
			}
			//

			if (l1ileri) {
				
				kontrol(moveL,lineLStations,l,L1);
				kontrol(moveL,lineLStations,p,L1);
				kontrol(moveL,lineLStations,o,L1);
				kontrol(moveL,lineLStations,k,L1);
				kontrol(moveL,lineLStations,g,L1);
				kontrol(moveL,lineLStations,c,L1);
				kontrol(moveL,lineLStations,d,L1);
				kontrol(moveL,lineLStations,h,L1);
				if(L1.waitTime==0) {
				moveL = (BusLine) lineL.peek();
				bosL.push(lineL.pop());
				}
				else {
					L1.waitTime--;
				}

			}
			if (l1geri) {
				
				kontrol(moveL,lineL2Stations,h,L1);
				kontrol(moveL,lineL2Stations,d,L1);
				kontrol(moveL,lineL2Stations,c,L1);
				kontrol(moveL,lineL2Stations,g,L1);
				kontrol(moveL,lineL2Stations,k,L1);
				kontrol(moveL,lineL2Stations,o,L1);
				kontrol(moveL,lineL2Stations,p,L1);
				kontrol(moveL,lineL2Stations,l,L1);
				
				if(L1.waitTime==0) {
				moveL = (BusLine) bosL.peek();
				lineL.push(bosL.pop());
				}
				else {
					L1.waitTime--;
				}
			
			}
			if (lineL.size() == 0) {
				l1geri = true;
				l1ileri = false;
			}
			if (bosL.size() == 0) {
				l1geri = false;
				l1ileri = true;
			}

			//
			if (l2ileri) {
				
				kontrol(moveL2,lineL2Stations,h,L2);
				kontrol(moveL2,lineL2Stations,d,L2);
				kontrol(moveL2,lineL2Stations,c,L2);
				kontrol(moveL2,lineL2Stations,g,L2);
				kontrol(moveL2,lineL2Stations,k,L2);
				kontrol(moveL2,lineL2Stations,o,L2);
				kontrol(moveL2,lineL2Stations,p,L2);
				kontrol(moveL2,lineL2Stations,l,L2);
				
				if(L2.waitTime==0) {
				moveL2 = (BusLine) lineTempL.peek();
				bosTempL.push(lineTempL.pop());
				}
				else {
					L2.waitTime--;
				}
		
				
			}
			if (l2geri) {
				
				kontrol(moveL2,lineLStations,l,L2);
				kontrol(moveL2,lineLStations,p,L2);
				kontrol(moveL2,lineLStations,o,L2);
				kontrol(moveL2,lineLStations,k,L2);
				kontrol(moveL2,lineLStations,g,L2);
				kontrol(moveL2,lineLStations,c,L2);
				kontrol(moveL2,lineLStations,d,L2);
				kontrol(moveL2,lineLStations,h,L2);
				if(L2.waitTime==0) {
				
				moveL2 = (BusLine) bosTempL.peek();
				lineTempL.push(bosTempL.pop());
				}
				else {
					L2.waitTime--;
				}
				
			}
			if (lineTempL.size() == 0) {
				l2geri = true;
				l2ileri = false;
			}
			if (bosTempL.size() == 0) {
				l2geri = false;
				l2ileri = true;
			}
			//

			if (m1ileri) {
				
				kontrol(moveM,lineMStations,md,M1);
				kontrol(moveM,lineMStations,n,M1);
				kontrol(moveM,lineMStations,j,M1);
				kontrol(moveM,lineMStations,f,M1);
				kontrol(moveM,lineMStations,g,M1);
				kontrol(moveM,lineMStations,k,M1);
				kontrol(moveM,lineMStations,o,M1);
				kontrol(moveM,lineMStations,p,M1);
				if(M1.waitTime==0) {
				moveM = (BusLine) lineM.peek();
				bosM.push(lineM.pop());
				}
				else {
					M1.waitTime--;
				}
			
			}
			if (m1geri) {
				
				kontrol(moveM,lineM2Stations,p,M1);
				kontrol(moveM,lineM2Stations,o,M1);
				kontrol(moveM,lineM2Stations,k,M1);
				kontrol(moveM,lineM2Stations,g,M1);
				kontrol(moveM,lineM2Stations,f,M1);
				kontrol(moveM,lineM2Stations,j,M1);
				kontrol(moveM,lineM2Stations,n,M1);
				kontrol(moveM,lineM2Stations,md,M1);
				if(M1.waitTime==0) {
				moveM = (BusLine) bosM.peek();
				lineM.push(bosM.pop());
				}
				else {
					M1.waitTime--;
				}
		
			}
			if (lineM.size() == 0) {
				m1geri = true;
				m1ileri = false;
			}
			if (bosM.size() == 0) {
				m1geri = false;
				m1ileri = true;
			}

			//
			if (m2ileri) {
				kontrol(moveM2,lineM2Stations,p,M2);
				kontrol(moveM2,lineM2Stations,o,M2);
				kontrol(moveM2,lineM2Stations,k,M2);
				kontrol(moveM2,lineM2Stations,g,M2);
				kontrol(moveM2,lineM2Stations,f,M2);
				kontrol(moveM2,lineM2Stations,j,M2);
				kontrol(moveM2,lineM2Stations,n,M2);
				kontrol(moveM2,lineM2Stations,md,M2);
				if(M2.waitTime==0) {
				moveM2 = (BusLine) lineTempM.peek();
				bosTempM.push(lineTempM.pop());
				}
				else {
					M2.waitTime--;
					
				}
		
			}
			if (m2geri) {
				kontrol(moveM2,lineMStations,md,M2);
				kontrol(moveM2,lineMStations,n,M2);
				kontrol(moveM2,lineMStations,j,M2);
				kontrol(moveM2,lineMStations,f,M2);
				kontrol(moveM2,lineMStations,g,M2);
				kontrol(moveM2,lineMStations,k,M2);
				kontrol(moveM2,lineMStations,o,M2);
				kontrol(moveM2,lineMStations,p,M2);
				
				if(M2.waitTime==0) { // yolcu indir-bindirde luggage iþlemleri için otobüsü durakta bekletmek için kullanýlýyor.
				moveM2 = (BusLine) bosTempM.peek();
				lineTempM.push(bosTempM.pop());
				}
				else {
					M2.waitTime--;
				}
				
			}
			if (lineTempM.size() == 0) {
				m2geri = true;
				m2ileri = false;
			}
			if (bosTempM.size() == 0) {
				m2geri = false;
				m2ileri = true;
			}
			
			
			Thread.sleep(1000); 
			// otobüslerin hareketini saðlayan Bus class ý altýndaki fonksiyonlarýnn çaðrýlýp otobüslerin  hareket ettirilmesi.
			A1.movementBus(map, moveA, previousA1, lineAStations);
			A2.movementBus(map, moveA2, previousA2, lineA2Stations);
			B1.movementBus(map, moveB, previousB1, lineBStations);
			B2.movementBus(map, moveB2, previousB2, lineB2Stations);
			C1.movementBus(map, moveC, previousC1, lineCStations);
			C2.movementBus(map, moveC2, previousC2, lineC2Stations);
			D1.movementBus(map, moveD, previousD1, lineDStations);
			D2.movementBus(map, moveD2, previousD2, lineD2Stations);
			L1.movementBus(map, moveL, previousL1, lineLStations);
			L2.movementBus(map, moveL2, previousL2, lineL2Stations);
			M1.movementBus(map, moveM, previousM1, lineMStations);
			M2.movementBus(map, moveM2, previousM2, lineM2Stations);
			if(belirleDurakOtobüs ==0) {// þayet arada kalan yukarýdaki iþlemler neticesinde duraða yolcu gelmiþse veya ayrýlmýþsa bunu tekrar yazýp güncellemek için kullanýlýyor.
				if(belirle==0) {writeInfoBusStop(a);}
				else if(belirle==1) {writeInfoBusStop(b);}
				else if(belirle==2) {writeInfoBusStop(c);}
				else if(belirle==3) {writeInfoBusStop(d);}
				else if(belirle==4) {writeInfoBusStop(e);}
				else if(belirle==5) {writeInfoBusStop(f);}
				else if(belirle==6) {writeInfoBusStop(g);}
				else if(belirle==7) {writeInfoBusStop(h);}
				else if(belirle==8) {writeInfoBusStop(ý);}
				else if(belirle==9) {writeInfoBusStop(j);}
				else if(belirle==10) {writeInfoBusStop(k);}
				else if(belirle==11) {writeInfoBusStop(l);}
				else if(belirle==12) {writeInfoBusStop(md);}
				else if(belirle==13) {writeInfoBusStop(n);}
				else if(belirle==14) {writeInfoBusStop(o);}
				else if(belirle==15) {writeInfoBusStop(p);}
				
			}

			// ekrana duraktaki yolcu sayýlarýný yazdýrma kodlarý.
			a.passengerNumberForBusStop(map, a.getCoor_x(), a.getCoor_y());
			b.passengerNumberForBusStop(map, b.getCoor_x(), b.getCoor_y());
			c.passengerNumberForBusStop(map, c.getCoor_x(), c.getCoor_y());
			d.passengerNumberForBusStop(map, d.getCoor_x(), d.getCoor_y());
			e.passengerNumberForBusStop(map, e.getCoor_x(), e.getCoor_y());
			f.passengerNumberForBusStop(map, f.getCoor_x(), f.getCoor_y());
			g.passengerNumberForBusStop(map, g.getCoor_x(), g.getCoor_y());
			h.passengerNumberForBusStop(map, h.getCoor_x(), h.getCoor_y());
			ý.passengerNumberForBusStop(map, ý.getCoor_x(), ý.getCoor_y());
			j.passengerNumberForBusStop(map, j.getCoor_x(), j.getCoor_y());
			k.passengerNumberForBusStop(map, k.getCoor_x(), k.getCoor_y());
			l.passengerNumberForBusStop(map, l.getCoor_x(), l.getCoor_y());
			md.passengerNumberForBusStop(map, md.getCoor_x(), md.getCoor_y());
			n.passengerNumberForBusStop(map, n.getCoor_x(), n.getCoor_y());
			o.passengerNumberForBusStop(map, o.getCoor_x(), o.getCoor_y());
			p.passengerNumberForBusStop(map, p.getCoor_x(), p.getCoor_y());
			
			//haritayý yazdýrma.
			m.printMap(map, cn);
		
		}
	}
}
