package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class PlayersChooserFrame extends JFrame {
    public PlayersChooserFrame() {
        super("Players List");
        setSize(500,500);
        setLayout(new BorderLayout());



        createMenuBar();
        createToolbar();
        createListPanel();
        createButtonsListener();
        createToolBarListeners();

    }
    private DefaultListModel<String> playersModel, teamModel;
    private JList<String> playersList, teamList;
    private JButton takeButton, returnButton, takeAllButton, returnAllButton;
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonsPanel.setLayout(new GridLayout(4,0));
        takeButton = new JButton(">");
        takeButton.setToolTipText("Add highlighted players");
        takeAllButton=new JButton(">>");
        takeAllButton.setToolTipText("Add all players");
        returnButton = new JButton("<");
        returnButton.setToolTipText("Return selected players");
        returnAllButton = new JButton("<<");
        returnAllButton.setToolTipText("Return all players");
        buttonsPanel.add(takeButton);
        buttonsPanel.add(takeAllButton);
        buttonsPanel.add(returnButton);
        buttonsPanel.add(returnAllButton);
        return buttonsPanel;
    }
    private void createToolBarListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String all = "";
                Enumeration<String> elements = teamModel.elements();
                while(elements.hasMoreElements()) {
                    all+=elements.nextElement() + "\n";
                }
                JOptionPane.showMessageDialog(getParent(), all,
                        "The following players have been selected:", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamModel.removeAllElements();
                playersModel.removeAllElements();
                for (String player: PlayersBase.getPlayers()) {
                    playersModel.addElement(player);
                }
            }
        });

    }

    private void createButtonsListener() {
        takeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                List<String> selection = playersList.getSelectedValuesList();
                for(String player:selection) {
                    teamModel.addElement(player);
                }
                for(String player:selection) {
                    playersModel.removeElement(player);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                List<String> selection = teamList.getSelectedValuesList();
                for (String player :selection) {
                    playersModel.addElement(player);
                }
                for(String player: selection) {
                    teamModel.removeElement(player);
                }
            }
        });
        takeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Enumeration<String> elements=playersModel.elements();
                while(elements.hasMoreElements()) {
                    String next = elements.nextElement();
                    teamModel.addElement(next);
                }
                playersModel.removeAllElements();
            }
        });
        returnAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Enumeration<String> elements = teamModel.elements();
                while(elements.hasMoreElements())
                {
                    String next = elements.nextElement();
                    playersModel.addElement(next);
                }
                teamModel.removeAllElements();
            }
        });
    }

    private void createListPanel() {
        playersModel = new DefaultListModel<String>();
        for (String player:PlayersBase.getPlayers()) {
            playersModel.addElement(player);
        }
        teamModel = new DefaultListModel<String>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        playersList = new JList<String>(playersModel);
        playersList.setToolTipText("Available players");
        playersList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        teamList = new JList<String>(teamModel);
        teamList.setToolTipText("Selected players");
        teamList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(playersList);
        panel.add(createButtonsPanel());
        panel.add(teamList);
        add(panel, BorderLayout.CENTER);

    }
    private JButton resetButton, saveButton;
    private void createToolbar() {
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset");
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());
        toolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        toolbar.add(saveButton);
        toolbar.add(resetButton);
        add(toolbar,BorderLayout.NORTH);

    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu filemenu= new JMenu("File");
        menubar.add(filemenu);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exitItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(getParent(), "Do you really want to go out??", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if(option==JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }


        });

        filemenu.add(exitItem);
    }
}
