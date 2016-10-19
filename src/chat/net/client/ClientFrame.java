package chat.net.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import chat.handler.Handler;
import chat.net.SocketConnector;
import chat.packet.BroadcastPacket;
import chat.packet.LoginPacket;
import chat.packet.LogoutPacket;
import chat.packet.SendPacket;
import chat.packet.UserListPacket;

/**
 * Created by wyx on 2016/10/19.
 */
public class ClientFrame extends JFrame {

    private Client client;

    private JButton btnSend = new JButton("Send");

    private JTextField txtMsg = new JTextField(20);

    private JTextPane txtHistory = new JTextPane();

    private List<String> userList = new ArrayList<String>(Arrays.asList("User List", ""));

    private JList<String> listUserList = new JList<String>(new String[]{"User List", ""});

    private StringBuffer sb = new StringBuffer();

    private JScrollPane scrollPane = new JScrollPane(txtHistory,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private ClientFrame(Client client) {
        super("chat room");
        this.client = client;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 800, 600);
        this.setLayout(new BorderLayout());
        this.initUI();
        this.initListener();
        client.login();
        this.setVisible(true);
    }

    private void initUI() {
        JPanel sendPanel = new JPanel();
        this.add(sendPanel, BorderLayout.SOUTH);
        sendPanel.add(txtMsg);
        sendPanel.add(btnSend);
        txtHistory.setContentType("text/html");
        txtHistory.setEditable(false);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(listUserList, BorderLayout.EAST);
    }

    private void initListener() {
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listUserList.getSelectedIndex() > 1) {
                    client.send(listUserList.getSelectedValue(), txtMsg.getText());
                } else {
                    client.broadcast(txtMsg.getText());
                }
                txtMsg.setText("");
            }
        };
        btnSend.addActionListener(l);
        txtMsg.addActionListener(l);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.logout();
                super.windowClosing(e);
            }
        });
        client.registerHandler(new Handler<LoginPacket>() {
            @Override
            public boolean handle(LoginPacket packet, SocketConnector connector) {
                userList.add(packet.getUsername());
                listUserList.setListData(userList.toArray(new String[userList.size()]));
                appendHistory(color("green", packet.getUsername()) + " login.</font>");
                return true;
            }
        });
        client.registerHandler(new Handler<LogoutPacket>() {

            @Override
            public boolean handle(LogoutPacket packet, SocketConnector connector) {
                userList.remove(packet.getUsername());
                listUserList.setListData(userList.toArray(new String[userList.size()]));
                appendHistory(color("red", packet.getUsername()) + " logout.</font>");
                return true;
            }
        });
        client.registerHandler(new Handler<SendPacket>() {
            @Override
            public boolean handle(SendPacket packet, SocketConnector connector) {
                appendHistory(color("blue", packet.getUsername()) + " to " + color("blue", packet.getTo())
                        + " : <br/>&nbsp;&nbsp;&nbsp;&nbsp;" + packet.getMsg());
                return true;
            }
        });
        client.registerHandler(new Handler<BroadcastPacket>() {
            @Override
            public boolean handle(BroadcastPacket packet, SocketConnector connector) {
                appendHistory(color("blue", packet.getUsername()) + " to " + color("blue", "all")
                        + " : <br/>&nbsp;&nbsp;&nbsp;&nbsp;" + packet.getMsg());
                return true;
            }
        });
        client.registerHandler(new Handler<UserListPacket>() {
            @Override
            public boolean handle(UserListPacket packet, SocketConnector connector) {
                userList.addAll(packet.getUserList());
                listUserList.setListData(userList.toArray(new String[userList.size()]));
                return true;
            }
        });
    }

    private String color(String color, String msg) {
        return "<font color='" + color + "'>" + msg + "</font>";
    }

    private void appendHistory(String msg) {
        sb.append(msg).append("<br/><br/>");
        txtHistory.setText(sb.toString());
    }

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog(null, "Please enter your user name.");
        Client client = new Client("127.0.0.1", 8888, username);
        new ClientFrame(client);
    }
}
