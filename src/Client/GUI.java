package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author K
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    String creds = "";
    String client_messages = "";
    String clients = "";
    Thread listen;
    Thread members_thread;
    Boolean isServerUp = false;
    String preMessages = "";
    Boolean isServerExisting = false;
    Boolean createdOwnServer = false;
    Server.ServerPanel server;
    Timer inactive_timer;

    public GUI() {
        initComponents();
        DefaultCaret caret = (DefaultCaret) jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        btnAddMember.setVisible(false);
        this.btnDisconnect.setEnabled(false);
    }

    private static class instance {

        private static final GUI INSTANCE = new GUI();
    }

    public static GUI getInstance() {
        return instance.INSTANCE;
    }

    public void startListening() {
        listen = new Thread(() -> {
            System.out.println("Started Listening");
            while (isServerUp || !this.s.isClosed()) {
                try {
                    Thread.sleep(10); //10 ms delay
                    // read the message sent to this client
                    this.sendMessage("refreshMessages", "");
                    String msg = dis.readUTF();
                    if (client_messages.length() != msg.length()) {
                        client_messages = msg;
                        WriteMessage(client_messages, true);
                    }

                    this.sendMessage("getClients", "");
                    String cli = dis.readUTF();
                    if (clients.length() != cli.length()) {
                        clients = cli;
                        String rt = clients.replace("[", "");
                        String rt2 = rt.replace("]", "");
                        String rt3 = rt2.replace(" ", "");
                        String[] cl = rt3.split(",");
                        List<String> cll = Arrays.asList(cl);
                        DefaultListModel lst = new DefaultListModel();
                        cll.stream().forEach((c) -> {
                            lst.addElement(c);
                        });
                        clientList.setModel(lst);
                    }
                } catch (IOException | InterruptedException | NumberFormatException e) {
                    this.DisplayError(e);
                }
            }
        });

        listen.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientList = new javax.swing.JList<>();
        btnAddMember = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtip = new javax.swing.JTextField();
        txtport = new javax.swing.JTextField();
        txtUID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        btnDisconnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        txtMessage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMessageFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMessageFocusLost(evt);
            }
        });
        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMessageKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtMessage);

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection Status"));

        lblIP.setText("IP ADDRESS:");

        lblPort.setText("Listening on port:");

        lblStatus.setText("Status:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPort)
                    .addComponent(lblStatus)
                    .addComponent(lblIP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Members"));

        clientList.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        jScrollPane3.setViewportView(clientList);

        btnAddMember.setText("OPEN NEW CLIENT WINDOW");
        btnAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddMember)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(btnAddMember)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection Status"));

        jLabel4.setText("IP ADDRESS: ");

        jLabel5.setText("PORT:");

        jLabel6.setText("USER ID:");

        txtip.setText("localhost");

        txtport.setText("3310");

        jLabel7.setText("USERNAME :");

        btnConnect.setText("CONNECT");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnDisconnect.setText("DISCONNECT");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtip))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtport)
                            .addComponent(txtUsername)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if (!this.s.isClosed()) {
            try {
                this.sendMessage("send", txtMessage.getText().replace(",", "\n").replace(".", ".\n"));
                this.txtMessage.setText("");
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSendActionPerformed

    public void sendMessage(String command, String message) throws IOException {
        try {
            dos.writeUTF(command + "#" + creds + message);
        } catch (IOException e) {
            this.DisplayError(e);
        }
    }

    public void readMessage() throws IOException {
        String msg = dis.readUTF();
        if (client_messages.length() != msg.length()) {
            client_messages = msg;
            WriteMessage(client_messages, true);
        }
    }

    public void WriteMessage(String m) {
        preMessages += "\n" + m + "\n";
        this.jTextArea1.append(preMessages);
    }

    public void WriteMessage(String m, Boolean Override) {
        if (Override) {
            this.jTextArea1.setText(preMessages + m);
        } else {
            preMessages += "\n" + m + "\n";
            this.jTextArea1.append(preMessages);
        }
    }


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (this.s != null) {
            if (!this.s.isClosed()) {
                try {
                    this.sendMessage("logout", creds);
                    this.s.close();
                    this.isServerUp = false;
                } catch (IOException ex) {
                    this.DisplayError(ex);
                }
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        // getting Server IP

        if (this.txtUID.getText().isEmpty() || this.txtUsername.getText().isEmpty()) {
            this.WriteMessage("Unable to connect. Please fill up the User ID and Username", true);
        } else {
            this.WriteMessage("Scanning the target server, Please wait..");

            this.btnConnect.setEnabled(false);
            //Async
            new Thread(() -> {
                try {
                    s = new Socket(InetAddress.getByName(this.txtip.getText()), Integer.parseInt(this.txtport.getText()));
                    isServerExisting = true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Unable to connect to server");
                    this.DisplayError(ex);
                    if (JOptionPane.showConfirmDialog(this, "No server was running in the network, do you want to create on your own?", "No Server", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        server = new Server.ServerPanel();
                        server.setPort(Integer.parseInt(this.txtport.getText()));
                        server.init();
                        server.setVisible(true);
                        createdOwnServer = true;
                        isServerExisting = true;
                    } else {
                        isServerExisting = false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Wrong format of Port, Expects an Integer");
                    this.DisplayError(e);
                    isServerExisting = false;
                }

                if (isServerExisting) {
                    this.connect_();
                }
                    this.btnConnect.setEnabled(true);
                }).start();

        }

    }//GEN-LAST:event_btnConnectActionPerformed

    private void connect_() {
        Boolean success = false;
        // establish the connection 
        try {
            if (createdOwnServer) {
                s = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(this.txtport.getText()));
                createdOwnServer = false;
            }

            // obtaining input and out streams 
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            this.creds = this.txtUID.getText() + "," + this.txtUsername.getText() + ", ";

            //login to the chat group
            if (!this.s.isClosed()) {
                this.WriteMessage("Connecting to Server");
                this.sendMessage("login", creds);
                String msg = dis.readUTF();
                success = Boolean.parseBoolean(msg.split("#")[0]);
                String message = msg.split("#")[1];
                WriteMessage(message);
            }

        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server");
            this.DisplayError(ex);
        }

        this.lblIP.setText("IP ADDRESS: " + this.txtip.getText());
        this.lblPort.setText("Listening on port: " + this.txtport.getText());
        this.lblStatus.setText("Status: Disconnected");

        if (success) {
            this.txtUID.setEditable(false);
            this.txtUsername.setEditable(false);
            this.txtip.setEditable(false);
            this.txtport.setEditable(false);
            this.btnConnect.setEnabled(false);
            this.btnDisconnect.setEnabled(true);
            this.isServerUp = true;
            this.btnSend.setEnabled(true);
            this.lblIP.setText("IP ADDRESS: " + this.txtip.getText());
            this.lblPort.setText("Listening on port: " + this.txtport.getText());
            this.lblStatus.setText("Status: Connected");
            this.WriteMessage("Logging in... Please wait");
            this.startListening();
            Timer_Control(false);
        }

    }
    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        this.disconnect();
    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        System.err.println(evt.getKeyCode());
    }//GEN-LAST:event_formKeyReleased

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.isControlDown()) {
            if (evt.getKeyCode() == 67) {
                if (JOptionPane.showConfirmDialog(this, "You've pressed the CTRL + C code, the program will exit. Do you want to exit?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        //btnAddMember

        if (JOptionPane.showConfirmDialog(this, "Do you want to run this client to test mode?", "Test Mode?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            btnAddMember.setVisible(true);
        } else {
            btnAddMember.setVisible(false);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnAddMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMemberActionPerformed
        new GUI().setVisible(true);
    }//GEN-LAST:event_btnAddMemberActionPerformed

    private void txtMessageFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMessageFocusLost
        Timer_Control(false);
    }//GEN-LAST:event_txtMessageFocusLost

    public void Timer_Control(Boolean stopTimer) {
        inactive_timer = new Timer();
        inactive_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GUI.getInstance().WriteMessage("You were disconnected due to 1 minute of inactivity");
                GUI.getInstance().disconnect();
            }
        }, (1000 * 60));

        if (stopTimer) {
            if (this.inactive_timer != null) {
                inactive_timer.cancel();
            }
        }
    }
    private void txtMessageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMessageFocusGained
        Timer_Control(true);
    }//GEN-LAST:event_txtMessageFocusGained

    private void txtMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyPressed
        if (evt.isControlDown()) {
            if (evt.getKeyCode() == 67) { //Press Ctrl + C
                if (JOptionPane.showConfirmDialog(this, "You've pressed the CTRL + C code, the program will exit. Do you want to exit?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }//GEN-LAST:event_txtMessageKeyPressed

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        if (evt.isControlDown()) {
            if (evt.getKeyCode() == 67) { //Press Ctrl + C
                if (JOptionPane.showConfirmDialog(this, "You've pressed the CTRL + C code, the program will exit. Do you want to exit?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void reset_socks() {
        try {
            this.s.close();
            this.isServerUp = false;
            this.txtUID.setEditable(true);
            this.txtUsername.setEditable(true);
            this.txtip.setEditable(true);
            this.txtport.setEditable(true);
            this.btnConnect.setEnabled(true);
            this.btnDisconnect.setEnabled(false);
            this.btnSend.setEnabled(false);
            this.clientList.setModel(new DefaultListModel<>());
            this.clients = "";
            jTextArea1.append("\n===============================\nYou were disconnected to the server\n===============================");
        } catch (IOException ex) {
            this.DisplayError(ex);
        }
    }

    private void DisplayError(Exception ex) {

        if (this.s != null) {
            System.err.println("IS SOCKET IS STILL OPEN?: " + !s.isClosed());
        }
        ErrorHandler.getInstance().Handle(ex, (Message, resetSock) -> {
            System.err.println(Message);
            if (resetSock) {
                this.reset_socks();
                JOptionPane.showMessageDialog(this, Message);
            }
        });
    }

    public void disconnect() {
        if (this.s.isConnected()) {
            try {
                this.sendMessage("logout", creds);
                this.reset_socks();

            } catch (IOException ex) {
                Logger.getLogger(GUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.lblIP.setText("IP ADDRESS: ");
        this.lblPort.setText("Listening on port: ");
        this.lblStatus.setText("Status: Disconnected");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            GUI.getInstance().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMember;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnSend;
    private javax.swing.JList<String> clientList;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextField txtUID;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtip;
    private javax.swing.JTextField txtport;
    // End of variables declaration//GEN-END:variables
}
