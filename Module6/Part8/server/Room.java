package Module6.Part8.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;

import Module6.Part8.common.Constants;

public class Room implements AutoCloseable {
	private Set<String> mutedClients = new HashSet<>();
	private String name;
	private List<ServerThread> clients = Collections.synchronizedList(new ArrayList<ServerThread>());
	private boolean isRunning = false;
	// Commands
	private final static String COMMAND_TRIGGER = "/";
	private final static String CREATE_ROOM = "createroom";
	private final static String JOIN_ROOM = "joinroom";
	private final static String DISCONNECT = "disconnect";
	private final static String LOGOUT = "logout";
	private final static String LOGOFF = "logoff";
	private final static String ROLL = "roll";
    private final static String FLIP = "flip";
	private final static String MUTE = "mute";
	private final static String UNMUTE = "unmute";
	private static Logger logger = Logger.getLogger(Room.class.getName());

	public Room(String name) {
		this.name = name;
		isRunning = true;
	}

	private void info(String message) {
		logger.log(Level.INFO, String.format("Room[%s]: %s", name, message));
	}

	public String getName() {
		return name;
	}

	public boolean isRunning() {
		return isRunning;
	}

	protected synchronized void addClient(ServerThread client) {
		if (!isRunning) {
			return;
		}
		client.setCurrentRoom(this);
		if (clients.indexOf(client) > -1) {
			info("Attempting to add a client that already exists");
		} else {
			clients.add(client);
			sendConnectionStatus(client, true);
			sendRoomJoined(client);
			sendUserListToClient(client);
		}
	}

	protected synchronized void removeClient(ServerThread client) {
		if (!isRunning) {
			return;
		}
		clients.remove(client);
		// we don't need to broadcast it to the server
		// only to our own Room
		if (clients.size() > 0) {
			// sendMessage(client, "left the room");
			sendConnectionStatus(client, false);
		}
		checkClients();
	}

	/***
	 * Checks the number of clients.
	 * If zero, begins the cleanup process to dispose of the room
	 */
	private void checkClients() {
		// Cleanup if room is empty and not lobby
		if (!name.equalsIgnoreCase("lobby") && clients.size() == 0) {
			close();
		}
	}
	 // mute list code
	
	private void saveMuteList() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("mutedClients.dat"))) {
            outputStream.writeObject(mutedClients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@SuppressWarnings("unchecked")
	private void loadMuteList() {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("mutedClients.dat"))) {
        Object object = inputStream.readObject();
        if (object instanceof Set) {
            mutedClients = (Set<String>) object; // Use the 'object' variable here
        } else {
            // Handle unexpected object type gracefully
            System.err.println("Unexpected object type found in mutedClients.dat");
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}
	@SuppressWarnings("unchecked")
    private static <T> T readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }

	/***
	 * Helper function to process messages to trigger different functionality.
	 * 
	 * @param message The original message being sent
	 * @param client  The sender of the message (since they'll be the ones
	 *                triggering the actions)
	 */
	private boolean processCommands(String message, ServerThread client) {
		boolean wasCommand = false;
		try {
			if (message.startsWith(COMMAND_TRIGGER)) {
				String[] comm = message.split(COMMAND_TRIGGER);
				String part1 = comm[1];
				String[] comm2 = part1.split(" ");
				String command = comm2[0];
				String roomName;
				wasCommand = true;
				switch (command) {
					case CREATE_ROOM:
						roomName = comm2[1];
						Room.createRoom(roomName, client);
						break;
					case JOIN_ROOM:
						roomName = comm2[1];
						Room.joinRoom(roomName, client);
						break;
					case DISCONNECT:
					case LOGOUT:
					case LOGOFF:
						Room.disconnectClient(client, this);
						break;
					case ROLL:
                        rollOnce(client, comm2);
                        break;
                    case FLIP:
                        flipToss(client);
                        break;
					case MUTE:
                        String[] parts = message.split("\\s+");
    					 if (parts.length >= 2) {
        				 String clientMute = parts[1];
       						 muteUser(client, clientMute);
   						} else {
        				sendMessage(client, "Format is invalid. Please use /mute <username>.");
   						 }
   					 	break;
                    case UNMUTE:
                         String[] partsOne = message.split("\\s+");
    					 if (partsOne.length >= 2) {
        				 String clientUnmute = partsOne[1];
       						 unmuteUser(client, clientUnmute);
   						} else {
        				sendMessage(client, "Format is invalid. Please use /mute <username>.");
   						 }
   					 	break;
					default:
						wasCommand = false;
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wasCommand;
	}

	// Command helper methods

	protected static void getRooms(String query, ServerThread client) {
		String[] rooms = Server.INSTANCE.getRooms(query).toArray(new String[0]);
		client.sendRoomsList(rooms, null);
	}

	protected static void createRoom(String roomName, ServerThread client) {
		if (Server.INSTANCE.createNewRoom(roomName)) {
			Room.joinRoom(roomName, client);
		} else {
			client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s already exists", roomName));
			client.sendRoomsList(null, String.format("Room %s already exists", roomName));
		}
	}

	protected static void joinRoom(String roomName, ServerThread client) {
		if (!Server.INSTANCE.joinRoom(roomName, client)) {
			client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s doesn't exist", roomName));
			client.sendRoomsList(null, String.format("Room %s doesn't exist", roomName));
		}
	}

	protected static void disconnectClient(ServerThread client, Room room) {

		client.setCurrentRoom(null);
		client.disconnect();
		room.removeClient(client);
	}

	//muted client code
	//This method is to mute the target user
	private void muteUser(ServerThread client, String clientMute) {
        ServerThread cIsMute = findUser(clientMute);
		if (cIsMute != null) {
			cIsMute.muteUser(client.getClientName()); // Mute the target client
			sendMessage(client, clientMute + " is now muted."); // Notify the muting client
			sendMessageToTarget(cIsMute, "You have been muted by " + client.getClientName()); // Notify the target client that they have been muted
			synchronized (clients) {
				if (!clients.contains(cIsMute)) {
					clients.add(cIsMute);  // Add back to broadcasting list
				}
			}
		} else {
			sendMessage(client, clientMute + " not found or already muted.");
		}
	}
	//unmute client code 
	//This method unmute the target user
	 private void unmuteUser(ServerThread client, String clientUnmute) {
        ServerThread cIsUnmute = findUser(clientUnmute);
		if (cIsUnmute != null) {
		cIsUnmute.unmuteUser(client.getClientName()); // Unmute the target client
		sendMessage(client, clientUnmute + " is now unmuted."); // Notify the muting client
		sendMessageToTarget(cIsUnmute, "You have been unmuted by " + client.getClientName()); // Notify the target client that they have been muted
		synchronized (clients) {
			if (!clients.contains(cIsUnmute)) {
				clients.add(cIsUnmute);  // Add back to broadcasting list
			}
		}
	} else {
		sendMessage(client, clientUnmute + " not found or already muted.");
	}
}
	private ServerThread findUser(String username) {
        // Search for the user with the specified username in the list of connected clients
        // and return the ServerThread if found.
        synchronized (clients) {
            for (ServerThread client : clients) {
                if (client.getClientName().equals(username)) {
                    return client;
                }
            }
        }
        return null;
    }

	private void rollOnce(ServerThread client, String[] rollPart1) {
            if (rollPart1.length == 2) {
                if (rollPart1[1].contains("d")) {
                    // Format 2: /roll #d#
                    diceRolls(client, rollPart1);
                } else {
                    // Format 1: /roll 0 - X or 1 - X
                    RandomRolls(client, rollPart1);
                }
            } else {
                sendMessage(client, "Invalid format. Please use /roll # or /roll #d#.");
            }
        }

		private void diceRolls(ServerThread client, String[] rollPart1) {
            try {
                String[] rollDice2 = rollPart1[1].split("d");
                int numDice = Integer.parseInt(rollDice2[0]);
                int numSides = Integer.parseInt(rollDice2[1]);
                if (numDice > 0 && numSides > 0) {
                    Random random = new Random();
                    int totalOutput = 0;
                    StringBuilder rMessage = new StringBuilder("Result: " + numDice + " dice with " + numSides + " sides: ");
                    for (int i = 0; i < numDice; i++) {
                        int output = random.nextInt(numSides) + 1;
                        rMessage.append(output);
                        totalOutput += output;
                        if (i < numDice - 1) {
                            rMessage.append(", ");
                        }
                    }
                    rMessage.append(". Total: ").append(totalOutput);
                    sendMessage(client, rMessage.toString());
                } else {
                    sendMessage(client, "Invalid values for the number of dice or number of sides.");
                }
            } catch (NumberFormatException e) {
                sendMessage(client, "Invalid values for the number of dice or number of sides.");
            }
        }

		private void RandomRolls(ServerThread client, String[] rollPart1) {
            try {
                int levelup = Integer.parseInt(rollPart1[1]);
                if (levelup  >= 0) {
                    Random random = new Random();
                    int output = random.nextInt(levelup + 1);
                    String rMessage = "Result: " + output;
                    sendMessage(client, rMessage);
                } else {
                    sendMessage(client, "Invalid value. Please enter new value.");
                }
            } catch (NumberFormatException e) {
                sendMessage(client, "Invalid value. Please enter new value.");
            }
        }

		 private void flipToss(ServerThread client) {
        Random random = new Random();
        int output = random.nextInt(2);
        String rMessage = (output == 0) ? "Coin flip is heads." :
                "Coin flip is tails.";
        sendMessage(client, rMessage);
    }

    
    public String TextChanges(String text) {
        // Changes text bold format (*...*)
    text = text.replaceAll("\\*(.*?)\\*", "<b>$1</b>");

    // Changes italics format (_..._)
    text = text.replaceAll("_(.*?)_", "<i>$1</i>");

    // Changes text color format (#...# or #rrggbb#)
    Pattern colorPattern = Pattern.compile("#(.*?)#");
    Matcher colorMatcher = colorPattern.matcher(text);
    while (colorMatcher.find()) {
        String color = colorMatcher.group(1);
        String replacement = "<font color=\"" + color + "\">$1</font>";
        //text = text.replace("#" + color + "#", replacement);
    }

    // Changes underline format (^...^)
    text = text.replaceAll("\\^(.*?)\\^", "<u>$1</u>");

	text = text.replaceAll("~(.*?)~", "<font color=\"green\">$1</font>");
	text = text.replaceAll("#(.*?)#", "<font color=\"blue\">$1</font>");
	text = text.replaceAll("!(.*?)!", "<font color=\"red\">$1</font>");

    return text;
}

	// end command helper methods

	/***
	 * Takes a sender and a message and broadcasts the message to all clients in
	 * this room. Client is mostly passed for command purposes but we can also use
	 * it to extract other client info.
	 * 
	 * @param sender  The client sending the message
	 * @param message The message to broadcast inside the room
	 */
	protected synchronized void sendMessage(ServerThread sender, String message) {
		if (!isRunning) {
			return;
		}
		info("Sending message to " + clients.size() + " clients");
		if (sender != null && processCommands(message, sender)) {
			// it was a command, don't broadcast
			return;
		}
		long from = (sender == null) ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
		  if (message.startsWith("@")) {
        // Pull the  username from the message privately
        String[] parts = message.split("\\s+", 2); // Split the message at the first space
        if (parts.length >= 2) {
            String whisperUser = parts[0].substring(1); // Remove the "@" symbol
            String privateMess = parts[1];
            ServerThread wUser = findUser(whisperUser);
            if (whisperUser != null) {
                // Send a private message to both the sender and targett user
                sendMessage(sender, "To @" + whisperUser + ": " + privateMess);
                sendMessage(wUser, "From @" + sender.getClientName() + ": " + privateMess);
			return;
            } else {
                sendMessage(sender, "User @" + whisperUser + " not found.");
            }
            return; // Do not broadcast the private message
        }
    }
		synchronized (clients) {
			Iterator<ServerThread> iter = clients.iterator();
			while (iter.hasNext()) {
				ServerThread client = iter.next();
				if (client.getClientName() != sender.getClientName() && !client.isMuted(sender.getClientName())) {
				String processedMessage = TextChanges(message);
				boolean messageSent = client.sendMessage(from, processedMessage);
				if (!messageSent) {
					handleDisconnect(iter, client);
				}
			}
		}
	}
}
	
	private synchronized void sendMessageToTarget(ServerThread targetClient, String message) {
    if (!isRunning) {
        return;
    }
    
    long from = (targetClient == null) ? Constants.DEFAULT_CLIENT_ID : targetClient.getClientId();
    synchronized (clients) {
        boolean messageSent = targetClient.sendMessage(from, message);
        if (!messageSent) {
            // Handle the case where the message couldn't be sent to the target client
            // This might involve removing the target client or performing other actions
            // based on your application's requirements.
            // handleTargetClientDisconnect(targetClient);
        }
    }
}

	protected synchronized void sendUserListToClient(ServerThread receiver) {
		logger.log(Level.INFO, String.format("Room[%s] Syncing client list of %s to %s", getName(), clients.size(),
				receiver.getClientName()));
		synchronized (clients) {
			Iterator<ServerThread> iter = clients.iterator();
			while (iter.hasNext()) {
				ServerThread clientInRoom = iter.next();
				if (clientInRoom.getClientId() != receiver.getClientId()) {
					boolean messageSent = receiver.sendExistingClient(clientInRoom.getClientId(),
							clientInRoom.getClientName());
					// receiver somehow disconnected mid iteration
					if (!messageSent) {
						handleDisconnect(null, receiver);
						break;
					}
				}
			}
		}
	}

	protected synchronized void sendRoomJoined(ServerThread receiver) {
		boolean messageSent = receiver.sendRoomName(getName());
		if (!messageSent) {
			handleDisconnect(null, receiver);
		}
	}

	protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
		// converted to a backwards loop to help avoid concurrent list modification
		// due to the recursive sendConnectionStatus()
		// this should only be needed in this particular method due to the recusion
		if (clients == null) {
			return;
		}
		synchronized (clients) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				ServerThread client = clients.get(i);
				boolean messageSent = client.sendConnectionStatus(sender.getClientId(), sender.getClientName(),
						isConnected);
				if (!messageSent) {
					clients.remove(i);
					info("Removed client " + client.getClientName());
					checkClients();
					sendConnectionStatus(client, false);
				}
			}
		}
	}

	private synchronized void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
		if (iter != null) {
			iter.remove();
		}
		info("Removed client " + client.getClientName());
		checkClients();
		sendConnectionStatus(client, false);
		// sendMessage(null, client.getClientName() + " disconnected");
	}

	public void close() {
		Server.INSTANCE.removeRoom(this);
		isRunning = false;
		clients = null;
	}
}