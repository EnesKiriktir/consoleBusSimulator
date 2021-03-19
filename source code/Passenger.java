package proje2;

import java.util.Random;

public class Passenger {
	Character baslangýcDurak,sonDurak;
	int passengerId, luggageCounter,luggageId,luggageId2;
	public Passenger(Character baslangýcDurak, Character sonDurak,int passengerId) { // bagaj yoksa
		this.baslangýcDurak = baslangýcDurak;
		this.sonDurak = sonDurak;
		this.passengerId = passengerId;
		this.luggageCounter = 0;
	}
	public Passenger(Character baslangýcDurak, Character sonDurak,int passengerId, int luggageId) { // 1 tane bagaj varsa 
		this.baslangýcDurak = baslangýcDurak;
		this.sonDurak = sonDurak;
		this.passengerId = passengerId;
		this.luggageId = luggageId;
		this.luggageCounter = 0;
	}
	public Passenger(Character baslangýcDurak, Character sonDurak,int passengerId, int luggageId,int luggageId2) { // 2 tane bagaj vasa
		this.baslangýcDurak = baslangýcDurak;
		this.sonDurak = sonDurak;
		this.passengerId = passengerId;
		this.luggageId = luggageId;
		this.luggageId2 = luggageId2;
		this.luggageCounter = 0;
	}
	
	
	public int getLuggageId2() {
		return luggageId2;
	}
	public void setLuggageId2(int luggageId2) {
		this.luggageId2 = luggageId2;
	}
	public Character getBaslangýcDurak() {
		return baslangýcDurak;
	}
	public void setBaslangýcDurak(Character baslangýcDurak) {
		this.baslangýcDurak = baslangýcDurak;
	}
	public Character getSonDurak() {
		return sonDurak;
	}
	public void setSonDurak(Character sonDurak) {
		this.sonDurak = sonDurak;
	}
	
	public int getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	public int getLuggageId() {
		return luggageId;
	}
	public void setLuggageId(int luggageId) {
		this.luggageId = luggageId;
	}
	public int getLuggageCounter() {
		return luggageCounter;
	}
	public void setLuggageCounter(int luggageCounter) {
		this.luggageCounter = luggageCounter;
	}
	
}
