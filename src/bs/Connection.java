package bs;

import java.util.LinkedList;
import java.util.List;

import comm.Message;

/**
 * Manages a particular type of connection to a remote device.
 */
public abstract class Connection {
	private List<MessageReceiver> messageReceivers;
	private List<ConnectionStateListener> connectionStateListeners;

	/**
	 * Constructor
	 * 
	 * Creates a new connection
	 */
	public Connection() {
		connectionStateListeners = new LinkedList<ConnectionStateListener>();
		messageReceivers = new LinkedList<MessageReceiver>();
	}
	
	/**
	 * Notifies all message receivers that a message has been received.
	 * The individual receivers then process the message in their own way
	 * @see bs.MessageReceiver.receiveMessage
	 * @param m The message that has been received
	 */
	protected void notifyMessageReceiver(Message m) {
		for (MessageReceiver messageReceiver : messageReceivers) {
			messageReceiver.receiveMessage(m);
		}
	}
	/**
	 * Notifies all connection state listeners that a connection has been established.
	 * The individual listeners then perform their specified actions to take upon establishing a connection.
	 * @see bs.ConnectionStateListener.connectionEstablished
	 */
	protected void notifyConnectionEstablished() {
		for (ConnectionStateListener listener : connectionStateListeners) {
			listener.connectionEstablished();
		}
	}
	/**
	 * Notifies all connection state listeners that a connection has been lost.
	 * The individual listeners then perform their specified actions to take upon loosing a connection.
	 * @see bs.ConnectionStateListener.connectionLost
	 */
	protected void notifyConnectionLost() {
		for (ConnectionStateListener listener : connectionStateListeners) {
			listener.connectionLost();
		}
	}
	/**
	 * Notifies all connection state listeners that a connection has been initiated, but has not been established yet.
	 * The individual listeners then perform their specified actions to take when waiting for a connection.
	 * @see bs.ConnectionStateListener.connecting
	 */
	protected void notifyConnecting() {
		for (ConnectionStateListener listener : connectionStateListeners) {
			listener.connecting();
		}
	}
	/**
	 * Notifies all connection state listeners that an attempt to connect failed.
	 * The individual listeners then perform their specified actions to take upon failing to establish a connection.
	 * @see bs.ConnectionStateListener.connectionAttemptFailed();
	 */
	protected void notifyConnectionAttemptFailed() {
		for (ConnectionStateListener listener : connectionStateListeners) {
			listener.connectionAttemptFailed();
		}
	}

	/**
	 * Constructor
	 * 
	 * Creates a new connection using the provided MessageReciever
	 * 
	 * @param receiver
	 *            The MessageReceiver to invoke upon receipt of messages
	 */
	public Connection(MessageReceiver receiver) {
		this();
		addMessageReceiver(receiver);
	}

	public abstract boolean isConnected();

	/**
	 * Tries to initiate a connection to a device with the given name and
	 * address.
	 * 
	 * Note that this is an asynchronous operation. Use a
	 * ConnectionStateListener to detect when a connection is established.
	 * 
	 * If the system is currently connected or trying to connect when this
	 * method is called, the existing connection or connection attempt will be
	 * terminated.
	 * 
	 * @param name
	 *            The name of the device to connect to
	 * @param address
	 *            The address of the device to connect to
	 */
	public abstract void connect(String name, String address);

	/**
	 * Terminates any existing connection and halts any current attempt to
	 * establish a connection.
	 */
	public abstract void disconnect();

	/**
	 * If connected, sends a message over the connection. Otherwise, does
	 * nothing.
	 * 
	 * @param m
	 *            The message to send
	 * @return True if able to successfully send the message, false otherwise
	 */
	public abstract boolean sendMessage(Message m);

	/**
	 * Registers the given MessageReceiver callback
	 * 
	 * @param receiver
	 *            The callback
	 */
	public void addMessageReceiver(MessageReceiver receiver) {
		messageReceivers.add(receiver);
	}

	/**
	 * Registers the given ConnectionStateListener callback
	 * 
	 * @param listener
	 *            The callback to notify of changes in connection state
	 */
	public void addConnectionStateListener(ConnectionStateListener listener) {
		this.connectionStateListeners.add(listener);
	}
}
