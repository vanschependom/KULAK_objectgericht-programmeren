package eigen;

public class CityTrip extends Event {

	private int cost = 0;
	private MeanOfTransport transport = null;

	public CityTrip(EventCategory cat, String location, int duration, int cost, MeanOfTransport transport) {
		super(cat, location, duration);
		setCost(cost);
		setTransport(transport);
	}

	public void sendNotification() {
		System.out.println("Time to leave!!");
	}

	public int getCost() {
		return cost;
	}

	private void setCost(int cost) {
		this.cost = cost;
	}

	public MeanOfTransport getTransport() {
		return transport;
	}

	private void setTransport(MeanOfTransport transport) {
		this.transport = transport;
	}
}
