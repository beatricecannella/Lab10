package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event> {

	enum EventType {
		INGRESSO,
		USCITA,
		TRACIMAZIONE,
		IRRIGAZIONE
	} ;

	private LocalDate time ;
	private EventType type ;
	private Flow flow ;
	
	public Event(LocalDate time, EventType type, Flow flow) {
		super();
		this.time = time;
		this.type = type;
		this.flow = flow;
	}
	
	
	
	
	public LocalDate getTime() {
		return time;
	}




	public void setTime(LocalDate time) {
		this.time = time;
	}




	public EventType getType() {
		return type;
	}




	public void setType(EventType type) {
		this.type = type;
	}




	public Flow getFlow() {
		return flow;
	}




	public void setFlow(Flow flow) {
		this.flow = flow;
	}




	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

}
