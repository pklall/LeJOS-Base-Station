package bs.ui;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bs.Connection;
import bs.ConnectionStateListener;
import bs.Log;

/**
 * A JPanel enabling the user to manage a Connection
 */
public class ConnectionManagerPanel extends JPanel implements
		ConnectionStateListener {
	private ConnectPanel connPanel;
	private DisconnectPanel disconnectPanel;
	private JLabel connectingLabel;

	private CardLayout cardLayout;

	private static final String CONNECT_CARD = "connect";
	private static final String CONNECTING_CARD = "connecting";
	private static final String DISCONNECT_CARD = "disconnect";

	/**
	 * Constructor
	 * 
	 * Creates a new Connection Manager to control the given connection
	 * 
	 * @param connection
	 *            The connection to manage
	 */
	public ConnectionManagerPanel(Connection connection) {
		connPanel = new ConnectPanel(connection);
		disconnectPanel = new DisconnectPanel(connection);
		connection.addConnectionStateListener(this);
		connectingLabel = new JLabel("Connecting...");

		cardLayout = new CardLayout();
		setLayout(cardLayout);

		add(connPanel, CONNECT_CARD);
		add(disconnectPanel, DISCONNECT_CARD);
		add(connectingLabel, CONNECTING_CARD);

		if (connection.isConnected()) {
			onConnected();
		} else {
			onDisconnected();
		}
	}

	private void onConnecting() {
		Log.verbose(this, "onConnecting()");
		cardLayout.show(this, CONNECTING_CARD);
	}

	private void onConnected() {
		Log.verbose(this, "onConnected()");
		cardLayout.show(this, DISCONNECT_CARD);
	}

	private void onDisconnected() {
		Log.verbose(this, "onDisconnected()");
		cardLayout.show(this, CONNECT_CARD);
	}

	@Override
	public void connectionEstablished() {
		onConnected();
	}

	@Override
	public void connectionLost() {
		onDisconnected();
	}

	@Override
	public void connecting() {
		onConnecting();
	}

	@Override
	public void connectionAttemptFailed() {
		onDisconnected();
	}
}
