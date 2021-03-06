package client;

import client.model.BModelClient;
import client.model.IModelClient;
import client.view.IObserver;
import client.view.SidePanel;
import client.view.ViewClient;
import common.COMMAND;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Client extends JFrame implements KeyListener, IObserver {
    IModelClient m;
    ViewClient view;
    private SidePanel sidePanel;

    private JPanel contentPane;
    private JPanel mainPane;
    private JPanel sidePane;
    private JTextArea logArea;

    Client() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        m = BModelClient.model();
        m.addObserver(this);

        setContentPane(contentPane);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    void initComponents() {
        view = new ViewClient();
        mainPane.setLayout(new GridLayout());
        mainPane.add(view);

        sidePanel = new SidePanel();
        sidePane.setLayout(new GridLayout());
        sidePane.add(sidePanel);

        logArea.setText("Game log:\n");
        logArea.setEditable(false);
        logArea.setFocusable(false);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Client vc = new Client();
                vc.setVisible(true);
                vc.setSize(800, 600);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int ch = keyEvent.getKeyCode();
        System.out.println(keyEvent);
        switch (ch) {
            case 65: case 37: {
                m.sendInstruction(COMMAND.LEFT);
            } break;
            case 68: case 39: {
                m.sendInstruction(COMMAND.RIGHT);
            } break;
            case 87: case 38: {
                m.sendInstruction(COMMAND.UP);
            } break;
            case 83: case 40: {
                m.sendInstruction(COMMAND.DOWN);
            } break;
            default: {
                break;
            }
        }
    }

    @Override
    public void refresh() {
        var queue = m.getLogQueue();
        for (var string : queue) {
            logArea.append(string + "\n");
        }
        m.clearLogQueue();
    }
}
