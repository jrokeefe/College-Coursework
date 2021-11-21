public class Passenger implements Comparable<Passenger> {
	
	private String name;
	private int time;
	private String seat;
	private int preferredBoarding = 0;
	private int doneTimeEstimate = 0;
	
    public Passenger(String name, int time, String seat) {
    		this.name = name;
    		this.time = time;
    		this.seat = seat;
    }
    public Passenger(String name, int time, String seat, int preferredBoarding) {
		this.name = name;
		this.time = time;
		this.seat = seat;
		this.preferredBoarding = preferredBoarding;
    }

    public void setDoneTimeEstimate(int estimate) {
    		doneTimeEstimate = estimate;
    }

    public int compareTo(Passenger other) { //Required for implementing Comparable
    		if (this.preferredBoarding < other.getPreferredBoarding()) {
    			return 1;
    		}
    		if (this.doneTimeEstimate > other.getDoneTimeEstimate()) {
    			System.out.println(other.getName());
    			System.out.println(other.getDoneTimeEstimate());
    			System.out.println(doneTimeEstimate);
    			return 1;
    		}
    		if (time < other.getTime()) {
    			return 0;
    		}
    		return 0;
    }
    
    public int getPreferredBoarding() {
    		return preferredBoarding;
    }
    
    public int getTime() {
    		return time;
    }
    
    public String getSeat() {
    		return seat;
    }
    
    public int getSeatNum() {
    		String number = seat.substring(0,seat.length() - 1);
    		return Integer.parseInt(number);
    }
    
    public char getSeatChar() {
    		return seat.charAt(seat.length() - 1);
    }
    
    public String getName() {
    		return name;
    }
    
    public void setTime(int time) {
    		this.time = time;
    }
    
    public int getDoneTimeEstimate() {
    		return doneTimeEstimate;
    }
}