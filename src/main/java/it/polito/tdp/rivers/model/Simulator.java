package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
	Model m;
	public Simulator () {
		m = new Model();
	}

	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	//modello del mondo (visione statica del sistema in un determinato istante di tempo;
	//all'interno metto le variabili che mi interessano del sistema che sto simulando)
//	private List<Flow> flows;
	private double Q;
	private double C;
	private List<Flow> fIn;
	private double fOut;
	private double fOutMin;
	//parametri simulazione
	

	//parametri di input
	private float k;
	private double fMedia;
	private River river;
	
	
	
	//parametri output
	private int giorniNoErogazione;
	private double cMed;
	
	public void init() {
		// inizializza coda eventi
		this.queue = new PriorityQueue<>();

		// inizializza modello del mondo
		//this.flows = new ArrayList<>();
		Q = k*this.fMedia*86400*30;
		C = Q/2;
		fIn = m.fDao.getRiverFlow(river);
		fOutMin = 0.8*this.fMedia*3600*24; //il flusso in uscita (fOut dovrà almeno essere = a fOutMin)
		
		//inizializzo i parametri di output
		this.giorniNoErogazione = 0;
		this.cMed = 0.0;
		
		for(Flow f: fIn) {
			this.queue.add(new Event(f.getDay(), EventType.INGRESSO, f)) ;
		}
	}
		
	//esegue la simulazione
		public void run() {
			while (!this.queue.isEmpty()) {
				Event e = this.queue.poll();
				//System.out.println(e);
				processEvent(e);
			}
		}
	
	private void processEvent(Event e) {
		Flow f = e.getFlow();
		//LocalDate data = e.getTime();
		int probabilita =  (int)(Math.random()*100);


		switch (e.getType()) {
		case INGRESSO:
			this.C += e.getFlow().getFlow()*24*3600;
			
			if(C>Q) {
				this.queue.add(new Event(e.getTime(), EventType.TRACIMAZIONE, f)) ;
			}
			else if(probabilita < 5) {
			this.queue.add(new Event(e.getTime(), EventType.IRRIGAZIONE, f)) ;
			} else {
				this.queue.add(new Event(e.getTime(), EventType.USCITA, f)) ;
			}
		
			break;
		
		case USCITA:
			if(C < this.fOutMin) {
				C = 0;
				this.giorniNoErogazione++;
			}else {
				C -= this.fOutMin; 
				this.cMed += C;
			}
			
			
		break;
		
		case TRACIMAZIONE:
			this.fOut = C - Q; //o creare altra variabile per la differenza?
			this.C -= fOut;
			break;
			
			
		case IRRIGAZIONE:
			this.fOut = 10*this.fOutMin;
			
			if(C < fOut) {
				// no irrigazione completa
				C = 0;
				this.giorniNoErogazione++;
			}else {
				C -= this.fOut; 
				this.cMed += C;
			}
				
			break;	
		}
		
		}
	
	public double capacitaMedia() {
		double cc = cMed/this.fIn.size();
		return cc;
	}
	
	public int giorniTotali() {
		return this.giorniNoErogazione;
	}


	public River getRiver() {
		return river;
	}



	public void setRiver(River river) { // da settare tramite FXMLController
		this.river = river;
	}



	public float getK() {
		return k;
	}
	
	
	
	public void setK(float kk) { // da settare tramite FXMLController
		this.k = kk;
	}

	public double getfMedia() {
		return fMedia;
	}

	public void setfMedia(double fMedia) {
		this.fMedia = fMedia;
	}
	
	
	
	
}
