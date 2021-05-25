package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.db.FlowDAO;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	RiversDAO rDao;
	FlowDAO fDao;
	LinkedList<Flow> riverFlows; 
	LocalDate first;
	LocalDate last;
	int cnt;
	float Q;
	public Simulator sim ;
	
	
	public Model() {
		rDao = new RiversDAO();
		fDao = new FlowDAO();
		cnt =0;
	}
	
	public LocalDate getFirstDate(River r) {
		riverFlows = new LinkedList<Flow>(fDao.getRiverFlow(r));
		 first = riverFlows.getFirst().getDay();
		
		return first;	
	}
	public LocalDate getLastDate() {	
		last = riverFlows.getLast().getDay();
		return last;
	}
	public int getMisurazioni(River r) {
		cnt = fDao.getConteggio(r);
		return cnt;
	}
	
	public double getMedia() {
		double media=0;
		
		for(Flow f: riverFlows) {
			media += f.getFlow(); 
		}
		
		return media/cnt;
		
	}
	
	public List<River> rivers(){
		return rDao.getAllRivers();
	}
	

}
