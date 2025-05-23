package Module7.Part10.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Module7.Part10.client.views.ChatPanel;
import Module7.Part10.client.views.ConnectionPanel;
import Module7.Part10.client.views.GamePanel;
import Module7.Part10.client.views.Menu;
import Module7.Part10.client.views.RoomsPanel;
import Module7.Part10.client.views.UserInputPanel;
import Module7.Part10.common.Constants;
import Module7.Part10.common.Payload;

public class ClientUI extends JFrame implements IClientEvents, ICardControls {
    CardLayout card = null;// accessible so we can call next() and previous()
    Container container;// accessible to be passed to card methods
    String originalTitle = null;
    private static Logger logger = Logger.getLogger(ClientUI.class.getName());
    private JPanel currentCardPanel = null;
    private Card currentCard = Card.CONNECT;

    private Hashtable<Long, String> userList = new Hashtable<Long, String>();
    private List<String> commonChatHistory = new ArrayList<>();
    

    private long myId = Constants.DEFAULT_CLIENT_ID;
    private JMenuBar menu;
    // Panels
    private ConnectionPanel csPanel;
    private UserInputPanel inputPanel;
    private RoomsPanel roomsPanel;
    private ChatPanel chatPanel;
    private GamePanel gamePanel;
    private Payload clientPayload;
    private boolean isMuted = false;
    private boolean isLastMessageSender = false;

    public ClientUI(String title) {
        
        super(title);// call the parent's constructor
        originalTitle = title;
        container = getContentPane();
        container.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // System.out.println("Resized to " + e.getComponent().getSize());
                // rough concepts for handling resize
                container.setPreferredSize(e.getComponent().getSize());
                container.revalidate();
                container.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });

        setMinimumSize(new Dimension(800, 600));
        // centers window
        setLocationRelativeTo(null);
        card = new CardLayout();
        setLayout(card);
        // menu
        menu = new Menu(this);
        this.setJMenuBar(menu);
        // separate views
        csPanel = new ConnectionPanel(this);
        inputPanel = new UserInputPanel(this);
        chatPanel = new ChatPanel(this);
        roomsPanel = new RoomsPanel(this, chatPanel, commonChatHistory);
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension((int)(this.getWidth()*.5), (int)this.getHeight()));
        chatPanel.add(gamePanel,BorderLayout.WEST);
    

        // https://stackoverflow.com/a/9093526
        // this tells the x button what to do (updated to be controlled via a prompt)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int response = JOptionPane.showConfirmDialog(container,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        Client.INSTANCE.sendDisconnect();
                    } catch (NullPointerException | IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
        // lastly
        pack();// tells the window to resize itself and do the layout management
        setVisible(true);
    }

    void findAndSetCurrentPanel() {
        for (Component c : container.getComponents()) {
            if (c.isVisible()) {
                currentCardPanel = (JPanel) c;
                currentCard = Enum.valueOf(Card.class, currentCardPanel.getName());
                // if we're not connected don't access anything that requires a connection
                if (myId == Constants.DEFAULT_CLIENT_ID && currentCard.ordinal() >= Card.CHAT.ordinal()) {
                    show(Card.CONNECT.name());
                }
                break;
            }
        }
        System.out.println(currentCardPanel.getName());
    }

    @Override
    public void next() {
        card.next(container);
        findAndSetCurrentPanel();
    }

    @Override
    public void previous() {
        card.previous(container);

    }

    @Override
    public void show(String cardName) {
        card.show(container, cardName);
        findAndSetCurrentPanel();
    }

    @Override
    public void addPanel(String cardName, JPanel panel) {
        this.add(cardName, panel);
    }

    @Override
    public void connect() {
        String username = inputPanel.getUsername();
        String host = csPanel.getHost();
        int port = csPanel.getPort();
        setTitle(originalTitle + " - " + username);
        Client.INSTANCE.connect(host, port, username, this);
        // TODO add connecting screen/notice
    }

    public static void main(String[] args) {
        new ClientUI("Client");
    }

    private String mapClientId(long clientId) {
        String clientName = userList.get(clientId);
        if (clientName == null) {
            clientName = "Server";
        }
        return clientName;
    }

    /**
     * Used to handle new client connects/disconnects or existing client lists (one
     * by one)
     * 
     * @param clientId
     * @param clientName
     * @param isConnect
     */
    private synchronized void processClientConnectionStatus(long clientId, String clientName, boolean isConnect) {
        if (isConnect) {
            if (!userList.containsKey(clientId)) {
                logger.log(Level.INFO, String.format("Adding %s[%s]", clientName, clientId));
                userList.put(clientId, clientName);
                chatPanel.addUserListItem(clientId, String.format("%s (%s)", clientName, clientId), isMuted, isLastMessageSender);
            }
        } else {
            if (userList.containsKey(clientId)) {
                logger.log(Level.INFO, String.format("Removing %s[%s]", clientName, clientId));
                userList.remove(clientId);
                chatPanel.removeUserListItem(clientId);
            }
            if (clientId == myId) {
                logger.log(Level.INFO, "I disconnected");
                myId = Constants.DEFAULT_CLIENT_ID;
                previous();
            }
        }
    }

    @Override
    public void onClientConnect(long clientId, String clientName, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, true);
            clientPayload = new Payload(); // Create a new Payload object
            clientPayload.setClientName(clientName);
            chatPanel.addText(String.format("*%s %s*", clientName, message), clientName);
        }
    }

    @Override
    public void onClientDisconnect(long clientId, String clientName, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, false);
            chatPanel.addText(String.format("*%s %s*", clientName, message),clientName);
        }
    }

    @Override
    public void onMessageReceive(long clientId, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            String clientName = mapClientId(clientId);
            chatPanel.addText(String.format("%s: %s", clientName, message),clientName);
        }
    }

    @Override
    public void onReceiveClientId(long id) {
        if (myId == Constants.DEFAULT_CLIENT_ID) {
            myId = id;
            show(Card.CHAT.name());
            gamePanel.attachListeners();
        } else {
            logger.log(Level.WARNING, "Received client id after already being set, this shouldn't happen");
        }
    }

    @Override
    public void onResetUserList() {
        userList.clear();
        chatPanel.clearUserList();
    }

    @Override
    public void onSyncClient(long clientId, String clientName) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, true);
        }
    }

    @Override
    public void onReceiveRoomList(String[] rooms, String message) {
        roomsPanel.removeAllRooms();
        if (message != null && message.length() > 0) {
            roomsPanel.setMessage(message);
        }
        if (rooms != null) {
            for (String room : rooms) {
                roomsPanel.addRoom(room);
            }
        }
    }

    @Override
    public void onRoomJoin(String roomName) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            //String clientName = clientPayload.getClientName();
            chatPanel.addText("Joined room " + roomName, mapClientId(myId));
        }
    }
}