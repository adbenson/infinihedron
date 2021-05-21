package infinihedron.control;

class StateManager {
	private static StateManager instance = null;

	public static StateManager getInstance() {
		if (instance == null) {
			instance = new StateManager();
		}
		return instance;
	}

	StateManager() {
		observableState = new ObservableProxy<State>(new StateImpl());
	}

	private final ObservableProxy<State> observableState;

	public State getCurrent() {
		return observableState.getObserved();
	}

	public void addChangeListener(ChangeListener<State> listener) {
		observableState.addChangeListener(listener);
	}
}
