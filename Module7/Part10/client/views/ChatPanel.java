package Module7.Part10.client.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.util.ArrayList;
import java.awt.Color;

import Module7.Part10.client.Card;
import Module7.Part10.client.Client;
import Module7.Part10.client.ClientUtils;
import Module7.Part10.client.ICardControls;
import Module7.Part10.common.Payload;


public class ChatPanel extends JPanel {
    private static Logger logger = Logger.getLogger(ChatPanel.class.getName());
    private JPanel chatArea = null;
    private UserListPanel userListPanel;
    private long clientId;
    private List<String> sameChatHistory = new ArrayList<>();
    

    private String lastMessageSenderName;

    public ChatPanel(ICardControls controls) {
        super(new BorderLayout(10, 10));
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        

        // wraps a viewport to provide scroll capabilities
        JScrollPane scroll = new JScrollPane(content);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        // no need to add content specifically because scroll wraps it
        wrapper.add(scroll);
        this.add(wrapper, BorderLayout.CENTER);

        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
        JTextField textValue = new JTextField();
        input.add(textValue);
        JButton button = new JButton("Send");
        // lets us submit with the enter key instead of just the button click
        textValue.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }


            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        button.addActionListener((event) -> {
            try {
                String text = textValue.getText().trim();
                Payload payload = new Payload();
                String clientName = payload.getClientName();
                if (text.length() > 0) {
                    Client.INSTANCE.sendMessage(text);
                    addMessageToHistory(text, clientName);
                    textValue.setText("");// clear the original text
                    addText(text, clientName);
                    // debugging
                    logger.log(Level.FINEST, "Content: " + content.getSize());
                    logger.log(Level.FINEST, "Parent: " + this.getSize());

                }
            } catch (NullPointerException e) {
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        chatArea = content;
        input.add(button);
        userListPanel = new UserListPanel(controls);
        this.add(userListPanel, BorderLayout.EAST);
        this.add(input, BorderLayout.SOUTH);
        this.setName(Card.CHAT.name());
        controls.addPanel(Card.CHAT.name(), this);
        chatArea.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {
                if (chatArea.isVisible()) {
                    chatArea.revalidate();
                    chatArea.repaint();
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (chatArea.isVisible()) {
                    chatArea.revalidate();
                    chatArea.repaint();
                }
            }

        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // System.out.println("Resized to " + e.getComponent().getSize());
                // rough concepts for handling resize
                // set the dimensions based on the frame size
                Dimension frameSize = wrapper.getSize();
                int w = (int) Math.ceil(frameSize.getWidth() * .3f);

                userListPanel.setPreferredSize(new Dimension(w, (int) frameSize.getHeight()));
                userListPanel.revalidate();
                userListPanel.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });
    }
        // Add a method to retrieve the chat history
    public List<String> goToChatHistory() {
        return sameChatHistory;
    }

    // Method to add a message to the chat history
    public void addMessageToHistory(String message, String clientName ) {
        String messageWithClient = clientName + ": " + message;
        sameChatHistory.add(messageWithClient);
    }

    public String getFormattedChatHistory() {
      StringBuilder history = new StringBuilder();
    for (String message :sameChatHistory) {
        history.append(message).append("\n");
    }
    return history.toString();
}
public boolean isLastMessageSender(String clientName) {
    return clientName.equals(lastMessageSenderName);
}

public void updateUserList(long[] clientIds, String[] clientNames, boolean[] isMutedArray) {
    int numUsers = Math.min(clientIds.length, Math.min(clientNames.length, isMutedArray.length));

    for (int i = 0; i < numUsers; i++) {
        long clientId = clientIds[i];
        String clientName = clientNames[i];
        boolean isMuted = isMutedArray[i];
        boolean isLastMessageSender = isLastMessageSender(clientName);
        addUserListItem(clientId, clientName, isMuted, isLastMessageSender);
    }
}

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void addUserListItem(long clientId, String clientName, boolean isMuted, boolean isLastMessageSender) {
        userListPanel.addUserListItem(clientId, clientName, isMuted, isLastMessageSender);
    }

    public void removeUserListItem(long clientId) {
        userListPanel.removeUserListItem(clientId);
    }

    public void clearUserList() {
        userListPanel.clearUserList();
    }

    public void addText(String text, String clientName) {
        JPanel content = chatArea;
        // add message
        JEditorPane textContainer = new JEditorPane("text/html",text);
        //Goes to New JE"<html><body><div>" + clientName + ": " + text + "<br></div></body></html>");
        //if (clientName != null && clientName.equals(lastSenderName)) {
        //    textContainer.setForeground(Color.BLUE); // Set your desired highlight color
        //}

        String senderClientName = clientName;
        boolean isLastMessageSender = isLastMessageSender(senderClientName);
        if (isLastMessageSender) {
            // Highlight the last message sender with a color
            textContainer.setForeground(Color.YELLOW); // Set your desired highlight color
        }

        // sizes the panel to attempt to take up the width of the container
        // and expand in height based on word wrapping
        textContainer.setLayout(null);
        textContainer.setPreferredSize(
        new Dimension(content.getWidth(), ClientUtils.calcHeightForText(this, text, content.getWidth())));
        textContainer.setMaximumSize(textContainer.getPreferredSize());
        textContainer.setEditable(false);
        ClientUtils.clearBackground(textContainer);
        // add to container and tell the layout to revalidate
        content.add(textContainer);
        // scroll down on new message
        JScrollBar vertical = ((JScrollPane) chatArea.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
