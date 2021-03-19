
package proje2;

import java.io.IOException;

public class Bus {
	String busId;
	int luggaceCapacity, passengerCapacity;
	Passenger[] passenger;
	Stack luggage;
	BusLine line;
	int waitTime;
	public Bus(String busId) {
		this.busId = busId;
		// this.line = line;
		this.luggaceCapacity = 8;
		this.passengerCapacity = 8;
		this.passenger = new Passenger[8];
		this.luggage = new Stack(8);
		this.waitTime = 0;
	}

	public void addNullToBus() { // bus daki passenger arrayine yolcu sayýmý kolay olsun diye nulll atandý.
		for (int i = 0; i < passenger.length; i++) {
			passenger[i] = null;
		}
	}

	public void addPassengerToBus(BusStop busstop, int j, Bus bus) { // j hangi yolcu olduðunu bulmak için kullanýldý.
													
			if (8 - bus.luggage.size() >= busstop.passenger[j].luggageCounter) {
				
				if (bus.passengerNumber() < 8) {
					for (int i = 0; i < bus.passenger.length; i++) {
						if (bus.passenger[i] == null) {
							bus.passenger[i] = busstop.passenger[j];
							if (busstop.passenger[j] != null) {

								if (busstop.passenger[j].luggageCounter == 1) {
									bus.luggage.push(busstop.passenger[j].getLuggageId());
									bus.waitTime++;
								} else if (busstop.passenger[j].luggageCounter == 2) {
									bus.luggage.push(busstop.passenger[j].getLuggageId());
									bus.luggage.push(busstop.passenger[j].getLuggageId2());
									bus.waitTime+=2;
								}
							}
							busstop.passenger[j] = null;

						}
					}
				}
			}
		

	}

	public void removeFromTheBus(Passenger p, Bus bus) { // otobüsten çýkarmaa fonksiyonu.
		Stack temp = new Stack(8);
		for (int i = 0; i < 8; i++) {
			if (passenger[i] == p) {
				if (p.luggageCounter != 0) {
					for (int j = 0; j < bus.luggage.size(); j++) {
						
					if(p.luggageCounter==2) {
						if (p.getLuggageId() == (int) bus.luggage.peek()
								|| p.getLuggageId2() == (int) bus.luggage.peek()) {
							bus.luggage.pop();
							bus.waitTime++;
						} else {
							temp.push(bus.luggage.pop());
						}
					}
					else {
						if (p.getLuggageId() == (int) bus.luggage.peek()) {
							bus.luggage.pop();
							bus.waitTime++;
						} else {
							temp.push(bus.luggage.pop());
						}
					}
						
					}
				}
				passenger[i] = null;
			}
		}

		while (!temp.isEmpty()) {
			bus.luggage.push(temp.pop());
		}
	}

	

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public int passengerNumber() { // otobüsteki yolcu sayýsýný veriyor.
		int passengerNumber = 0;
		for (int i = 0; i < 8; i++) {
			if (passenger[i] != null) {
				passengerNumber++;
			}
		}
		return passengerNumber;
	}

	public void movementBus(String[][] map, BusLine move, BusLine previous, BusStop[] arrayLine) { // arrayline da
																									// otobüsün gideceði
																									// güzergahtaki
																									// duraklar BusStop
																									// tipinde tutuldu.
		map[move.getCoor_x()][move.getCoor_y()] = Integer.toString(passengerNumber());
		// String numberOfPassenger = String.valueOf(passengerNumber()); //otobüsteki
		// mevcut yolcu sayýsýný stringe çevirdik çünkü map array i string tipinde.
		// saða doðru hareket kodu
		if (move.getCoor_x() == previous.getCoor_x() && move.getCoor_y() == previous.getCoor_y() + 1) { // hareket kodlarý otobüsün bulunduðu koordinatlar ile management da 
																										//	stack den alýnan bir önceki yerikoordinatlarý karþýlaþtýrýlarak yapýldý.

			map[move.getCoor_x()][move.getCoor_y() - 1] = " ";
			for (int i = 0; i < arrayLine.length; i++) {
				if (previous.getCoor_x() == arrayLine[i].getCoor_x()
						&& previous.getCoor_y() == arrayLine[i].getCoor_y()) {
					map[previous.getCoor_x()][previous.getCoor_y()] = arrayLine[i].getName();
				}
			}
		}

		// sola doðru hareket kodu
		if (move.getCoor_x() == previous.getCoor_x() && move.getCoor_y() == previous.getCoor_y() - 1) {
			// map[move.getCoor_x()][move.getCoor_y()] = "X";
			map[move.getCoor_x()][move.getCoor_y() + 1] = " ";
			for (int i = 0; i < arrayLine.length; i++) {
				if (previous.getCoor_x() == arrayLine[i].getCoor_x()
						&& previous.getCoor_y() == arrayLine[i].getCoor_y()) {
					map[previous.getCoor_x()][previous.getCoor_y()] = arrayLine[i].getName();
				}
			}
		}

		// yukarý doðru hareket kodu

		if (move.getCoor_x() == previous.getCoor_x() - 1 && move.getCoor_y() == previous.getCoor_y()) {
			// map[move.getCoor_x()][move.getCoor_y()] ="X";
			map[move.getCoor_x() + 1][move.getCoor_y()] = " ";
			for (int i = 0; i < arrayLine.length; i++) {
				if (previous.getCoor_x() == arrayLine[i].getCoor_x()
						&& previous.getCoor_y() == arrayLine[i].getCoor_y()) {
					map[previous.getCoor_x()][previous.getCoor_y()] = arrayLine[i].getName();
				}
			}
		}

		// aþaðý doðru hareket kodu

		if ((move.getCoor_x() == previous.getCoor_x() + 1) && (move.getCoor_y() == previous.getCoor_y())) {
			// map[move.getCoor_x()][move.getCoor_y()] = "X";
			map[move.getCoor_x() - 1][move.getCoor_y()] = " ";
			for (int i = 0; i < arrayLine.length; i++) {
				if (previous.getCoor_x() == arrayLine[i].getCoor_x()
						&& previous.getCoor_y() == arrayLine[i].getCoor_y()) {
					map[previous.getCoor_x()][previous.getCoor_y()] = arrayLine[i].getName();
				}
			}
		}

	}

	public String getBusId() {
	
		return busId;
	}

}
