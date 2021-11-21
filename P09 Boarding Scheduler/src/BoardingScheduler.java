import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BoardingScheduler {
	
	private Iterator<Passenger> passengers;
	private ArrayList<Passenger> unsortedList;
	private ArrayList<Passenger> sortedList;
	private int time;

	public BoardingScheduler(Iterator<Passenger> passengers, int startTime) {
		this.passengers = passengers;
		unsortedList = new ArrayList<Passenger>();
		sortedList = new ArrayList<Passenger>();
		this.time = startTime;
		while(this.passengers.hasNext()) {
			unsortedList.add(this.passengers.next());
		}
	}
	
	
	
	public int calculateDoneTimeEstimate(Passenger p) {		
		if (sortedList.isEmpty()) {
			return time + 5;
		}
		for (int i = 0; i < 5; ++i) {
			if (sortedList.size() - 1 - i < 0) {
				break;
			}
			Passenger before = sortedList.get(sortedList.size() - 1 - i);
			if (before.getSeatNum() <= p.getSeatNum()) {
				return before.getDoneTimeEstimate() + 5;
			}
		}
		return time + 5;
	}
	
	private Passenger first() {
		int bestIndex = 0;
			for(int i = 1; i<unsortedList.size(); ++i) {
				Passenger best = unsortedList.get(bestIndex);
				Passenger check = unsortedList.get(i);
				if (check.getTime() > time) {
					break;
				}
				if(best.compareTo(check) > 0) {
					bestIndex = i;
				}
			}
		Passenger ret = unsortedList.get(bestIndex);
		if (ret.getTime() < time) {
			ret.setTime(time);
		}
		else {
			time = ret.getTime();
		}
		ret.setDoneTimeEstimate(time + 5);
		sortedList.add(ret);
		++time;
		unsortedList.remove(bestIndex);
		return ret;
	}
	
	private Passenger schedule() {
		int bestIndex = 0;
		for(int i = 1; i < unsortedList.size(); ++i) {
			Passenger best = unsortedList.get(bestIndex);
			best.setDoneTimeEstimate(calculateDoneTimeEstimate(best));
			if (best.getTime() > time) {
				time = best.getTime();
			}
			Passenger check = unsortedList.get(i);
			check.setDoneTimeEstimate(calculateDoneTimeEstimate(check));
			if(check.getTime() > time) {
				break;
			}
			if(best.compareTo(check) > 0) {
				bestIndex = i;
			}
		}
		Passenger ret = unsortedList.get(bestIndex);
		unsortedList.remove(bestIndex);
		ret.setDoneTimeEstimate(calculateDoneTimeEstimate(ret));
		ret.setTime(time++);
		sortedList.add(ret);
		return ret;
	}
	
	private ArrayList<Passenger> in(int time) {
		ArrayList<Passenger> ret = new ArrayList<>();
		for (int i = 0; i < unsortedList.size(); ++i) {
			if (unsortedList.get(i).getTime() > time) {
				break;
			}
			if (unsortedList.get(i).getTime() == time) {
				ret.add(unsortedList.get(i));
				unsortedList.remove(i);
				--i;
			}
		}
		return ret;
	}
	
	public boolean isEmpty() {
		return unsortedList.isEmpty();
	}
	
	public static void boardFlight(Iterator<Passenger> passengers, int startTime) {
		BoardingScheduler scheduler = new BoardingScheduler(passengers, startTime);
		BoardingHeap passengerHeap = new BoardingHeap();
		//passengerHeap.enqueue(scheduler.first());
		int i = 0;
		while(passengers.hasNext()) {
			ArrayList<Passenger> checkIn = scheduler.in(startTime + i);
			for (int j = 0; j < checkIn.size(); ++j) {
				passengerHeap.enqueue(checkIn.get(j));
			}
			++i;
		}
		System.out.println(startTime + " Boarding begins\n");
		while (!passengerHeap.isEmpty()) {
			Passenger p = passengerHeap.dequeue();
			System.out.println(p.getTime() + " " + p.getName() + " " + p.getSeat() + " (done " + p.getDoneTimeEstimate() + ")");
			if (passengerHeap.isEmpty()) {
				System.out.print(p.getDoneTimeEstimate() + 1);
			}
		}
		System.out.println(" All passengers have boarded!");
	}
	
	/**
	 * Reads in a file containing a unsortedList of flight passengers in the order they
	 * check in and runs the boardFlight() method with those passengers.
	 * @author Tina, Alexi
	 * @param flight is the name of the input file in the project directory
	 */
	public static void checkIn(String flight) {
	    File f = new File(flight);
	    try {
	        Scanner s = new Scanner(f);
	        List<Passenger> passengers = new ArrayList<Passenger>();
	        while(s.hasNextLine()) {
	            //Data are separated by commas and possibly also whitespace.
	            String[] line = s.nextLine().split("\\s*,\\s*");
	            if (line.length == 3) //Default preferredBoarding 0 constructor
	                passengers.add(new Passenger(line[0],
	                        Integer.parseInt(line[1]),
	                        line[2]));
	            else //Use the preferredBoarding number if given
	                passengers.add(new Passenger(line[0],
	                        Integer.parseInt(line[1]),
	                        line[2],
	                        Integer.parseInt(line[3])));
	        }
	        s.close();
	        boardFlight(passengers.iterator(), 0);
	    } catch (IOException e) {
	        System.out.println("Error: Unable to load file " + flight);
	    }
	}
	
	public static void main(String[] args) {
		checkIn("files/sample2.txt");
		
	}
	
}
