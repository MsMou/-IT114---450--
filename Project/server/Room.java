package Project.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.Random;

import Project.common.Constants;


public class Room implements AutoCloseable {
    // server is a singleton now so we don't need this
    // protected static Server server;// used to refer to accessible server
    // functions
    private String name;
    private List<ServerThread> clients = new ArrayList<ServerThread>();
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
    private static Logger logger = Logger.getLogger(Room.class.getName());

    public Room(String name) {
        this.name = name;
        isRunning = true;
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
            logger.warning("Attempting to add client that already exists in room");
        } else {
            clients.add(client);
            client.sendResetUserList();
            syncCurrentUsers(client);
            sendConnectionStatus(client, true);
        }
    }

    protected synchronized void removeClient(ServerThread client) {
        if (!isRunning) {
            return;
        }
        // attempt to remove client from room
        try {
            clients.remove(client);
        } catch (Exception e) {
            logger.severe(String.format("Error removing client from room %s", e.getMessage()));
            e.printStackTrace();
        }
        // if there are still clients tell them this person left
        if (clients.size() > 0) {
            sendConnectionStatus(client, false);
        }
        checkClients();
    }

    private void syncCurrentUsers(ServerThread client) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread existingClient = iter.next();
            if (existingClient.getClientId() == client.getClientId()) {
                continue;// don't sync ourselves
            }
            boolean messageSent = client.sendExistingClient(existingClient.getClientId(),
                    existingClient.getClientName());
            if (!messageSent) {
                handleDisconnect(iter, existingClient);
                break;// since it's only 1 client receiving all the data, break if any 1 send fails
            }
        }
    }

    /***
     * Checks the number of clients.
     * If zero, begins the cleanup process to dispose of the room
     */
    private void checkClients() {
        // Cleanup if room is empty and not lobby
        if (!name.equalsIgnoreCase(Constants.LOBBY) && (clients == null || clients.size() == 0)) {
            close();
        }
    }

    /***
     * Helper function to process messages to trigger different functionality.
     * 
     * @param message The original message being sent
     * @param client  The sender of the message (since they'll be the ones
     *                triggering the actions)
     */
    @Deprecated // not used in my project as of this lesson, keeping it here in case things
                // change
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
        client.sendRoomsList(rooms,
                (rooms != null && rooms.length == 0) ? "No rooms found containing your query string" : null);
    }

    protected static void createRoom(String roomName, ServerThread client) {
        if (Server.INSTANCE.createNewRoom(roomName)) {
            Room.joinRoom(roomName, client);
        } else {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s already exists", roomName));
        }
    }

    /**
     * Will cause the client to leave the current room and be moved to the new room
     * if applicable
     * 
     * @param roomName
     * @param client
     */
    protected static void joinRoom(String roomName, ServerThread client) {
        if (!Server.INSTANCE.joinRoom(roomName, client)) {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s doesn't exist", roomName));
        }
    }

    protected static void disconnectClient(ServerThread client, Room room) {
        client.disconnect();
        room.removeClient(client);
    }
    //creating Format 1 and Format 2
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
                    StringBuilder rMessage = new StringBuilder("Resut: " + numDice + " dice with " + numSides + " sides: ");
                    for (int i = 0; i < numDice; i++) {
                        int output = random.nextInt(numSides) + 1;
                        rMessage.append(output);
                        totalOutput += output;
                        if (i < numDice - 1) {
                            rMessage.append(", ");
                        }
                    }
                    rMessage.append(". Total: ").append(totalOutput);
                    sendMessage(null, rMessage.toString());
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
                    sendMessage(null, rMessage);
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
        sendMessage(null, rMessage);
    }

    
    public String TextChanges(String text) {
    // Changes text bold format (*...*)
    System.out.println("Before bold replacement: " + text);
    text = text.replace("*", "<b>");
    System.out.println("After bold replacement: " + text);

    // Changes italics format (_..._)
    System.out.println("Before italics replacement: " + text);
    text = text.replace("_", "<i>");
    System.out.println("After italics replacement: " + text);

    // Changes text color format (#...# or #rrggbb#)
    System.out.println("Before color replacement: " + text);
    text = text.replace("#", "<font color=\"#");
    System.out.println("After color replacement: " + text);

    // Changes underline format (^...^)
    System.out.println("Before underline replacement: " + text);
    text = text.replace("^", "<u>");
    System.out.println("After underline replacement: " + text);

    // Close formatting tags
    System.out.println("Before closing tags: " + text);
    text = text.replace("*", "</b>");
    text = text.replace("_", "</i>");
    text = text.replace("#", "\">");
    text = text.replace("^", "</u>");
    System.out.println("After closing tags: " + text);

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
        logger.info(String.format("Sending message to %s clients", clients.size()));
        if (sender != null && processCommands(message, sender)) {
            // it was a command, don't broadcast
            return;
        }
        long from = sender == null ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread client = iter.next();
            String processedMessage = TextChanges(message);
            boolean messageSent = client.sendMessage(from, processedMessage);
            if (!messageSent) {
                handleDisconnect(iter, client);
            }
        }
    }

    protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread receivingClient = iter.next();
            boolean messageSent = receivingClient.sendConnectionStatus(
                    sender.getClientId(),
                    sender.getClientName(),
                    isConnected);
            if (!messageSent) {
                handleDisconnect(iter, receivingClient);
            }
        }
    }

    private void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
        iter.remove();
        logger.info(String.format("Removed client %s", client.getClientName()));
        sendMessage(null, client.getClientName() + " disconnected");
        checkClients();
    }

    public void close() {
        Server.INSTANCE.removeRoom(this);
        isRunning = false;
        clients.clear();
    }
}