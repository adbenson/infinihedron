package infinihedron.control;

public abstract class Observed {

	private Observer observer;

	public Observed(Observer observer) {
		this.observer = observer;
	}

	public void notifyObservers() {
		this.observer.notifyChange();
	}
}
