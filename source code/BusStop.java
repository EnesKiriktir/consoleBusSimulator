
package proje2;
public class BusStop {
	String name;
	Passenger [] passenger;
	int coor_x;
	int coor_y;
	public BusStop(String name,int coor_x, int coor_y) {
		this.name = name;
		this.coor_x = coor_x;
		this.coor_y = coor_y;
		this.passenger = new Passenger[9];
	}
	
	
	public Passenger[] getPassenger() {
		return passenger;
	}


	public void setPassenger(Passenger[] passenger) {
		this.passenger = passenger;
	}


	public void addNullToBusStop() { // yolcu sayýsýný daha rahat saymak için null atanýyor.
		for(int i=0;i<passenger.length;i++) {
			passenger[i] =null;
		}
	}
	
	public void addPassengerToBusStop(Passenger p) { // yolcu ekleme fonksiyonu.
		for(int i=0;i<9;i++) {
			if(passenger[i]==null) {
				passenger[i]=p;
				break;
			}
		}
	}
	public void removeFromTheBusStop(Passenger p) { // yolcu indirme fonksiyonu.
		for(int i=0;i<9;i++) {
			if(passenger[i]==p) {
				passenger[i]=null;
			}
		}
	}
	
	public int getCoor_x() {
		return coor_x;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCoor_x(int coor_x) {
		this.coor_x = coor_x;
	}
	public int getCoor_y() {
		return coor_y;
	}
	public void setCoor_y(int coor_y) {
		this.coor_y = coor_y;
	}
	
	

	public void passengerNumberForBusStop(String[][] map,int x,int y) { // bus stop daki toplam yolcu sayýsýný veren fonksiyon.
int counter=0; // kaç yolcu olduðunu saymak için tuttuk.

for(int i=0;i<passenger.length;i++) {
	if(passenger[i]!=null) {
		counter++;
	}
}
String numberOfPassenger = String.valueOf(counter); // int ifadeyi stringe çevirdik çünkü map string türünde.
for(int i = 0;i<map.length;i++) {
	for(int j=0;j<map[0].length;j++) {
		map[x+1][y+1] = numberOfPassenger;
	}
}
}
	public int passengercounter() {
		int counter=0;

		for(int i=0;i<passenger.length;i++) {
			if(passenger[i]!=null) {
				counter++;
			}
	}
		return counter;
	}
}
