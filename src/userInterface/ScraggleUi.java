/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.*;

/**
 *
 * @author serin
 */
public class ScraggleUi {
    JFrame frame;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JMenuItem menuItem2;
    JPanel holdCurrentword;
    JButton[][] dice;
    JPanel holdDice;
    JScrollPane scrollPane;
    JTextPane enterWord;
    JLabel currentWord;
    JButton submitWord;
    JPanel holdingPanel;
    JLabel playerScore;
    JButton shakeDice;
    JLabel showTime;
    Game game;
    
    private int score;
    private Timer timer;
    private int minutes = 1;
    private int seconds = 0;
    private ArrayList<String> foundWords;
    private ResetGameListener resetGameListener;
    
    //colors for the interface
        Color extraLightpink = new Color(250,210,225);
        Color lightPink = new Color(255,179,198);
        Color lightYellow = new Color(255,253,237);
        
    public ScraggleUi(Game inGame)
    {
        game = inGame;
        
        initObjects();
        initComponents();
    }
    
    private void initObjects()
    {
        resetGameListener = new ResetGameListener();
        foundWords = new ArrayList<String>();
    }
    
    
    private void initComponents()
    {
        
        
        //frame for the scraggle game
        frame = new JFrame ("Scraggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(900,550));
        
        //creating the menu bar
        menuBar = new JMenuBar();
        
        //creating the main menu
        menu = new JMenu("Menu");
        menu.setText("Scraggle");
        menu.setMnemonic('s');
        
        //creates items for the menu
        menuItem = new JMenuItem("New Game");
        menuItem2 = new JMenuItem("Exit");
        menuItem.addActionListener(new ResetGameListener());
        menuItem2.addActionListener(new ExitListener());
        
        //adds menu items to the menu
        menu.add(menuItem);
        menu.add(menuItem2);
        
        //adds total menu to the frame
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        
        //creates panel that holds the current words, submit button, and score
        holdCurrentword = new JPanel();
        currentWord = new JLabel();
        
        //creates the title and gives a color and adds it to the panel
        holdCurrentword.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Current Word", TitledBorder.LEFT, TitledBorder.TOP));        
        holdCurrentword.setBackground(lightPink);
        holdCurrentword.add(currentWord, BorderLayout.PAGE_START);
        
        //gives the current word box a size and title
        currentWord.setPreferredSize(new Dimension(400,65));
        currentWord.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Current Word", TitledBorder.LEFT, TitledBorder.TOP));
        
        //creates and formats submit word button, adds it to the panel
        submitWord = new JButton("Submit Word");
        submitWord.setPreferredSize(new Dimension(300, 65));
        submitWord.setFont(new Font("Serif", Font.PLAIN, 30));
        submitWord.setBackground(extraLightpink);
        submitWord.addActionListener(new SubmitListener());
        holdCurrentword.add(submitWord);
        
        //creates and formats a score box, adds it to the panel
        playerScore = new JLabel();
        playerScore.setPreferredSize(new Dimension(130,65));
        playerScore.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Current Score", TitledBorder.LEFT, TitledBorder.TOP));
        holdCurrentword.add(playerScore);
        
        holdCurrentword.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //creates a new panel and a 2D array of dice
        holdDice = new JPanel();
        dice = new JButton[4][4];
        
        //creates Grid Layout
        holdDice.setLayout(new GridLayout(4,4));
        
        //creates and formats each die, adds it into the Grid Layout
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                URL imgPath = getClass().getResource(game.getGrid()[i][j].getImgPath());
                ImageIcon icon = new ImageIcon(imgPath);
                dice[i][j] = new JButton(icon);
                dice[i][j].putClientProperty("row", i);
                dice[i][j].putClientProperty("col", j);
                dice[i][j].putClientProperty("letter", game.getGrid()[i][j].getLetter());
                dice[i][j].setPreferredSize(new Dimension(125,85));
                dice[i][j].setBackground(Color.getHSBColor(196, 175, 210));
                dice[i][j].addActionListener(new TileListener());
                dice[i][j].addActionListener(new LetterListener());
                holdDice.add(dice[i][j]);
            }
        }
        
        //formats the panel
        holdDice.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Scraggle Board", TitledBorder.LEFT, TitledBorder.TOP));
        holdDice.setBackground(lightPink);
        
        
        //creates and formats new panel
        holdingPanel = new JPanel();
        
        holdingPanel.setLayout(new GridLayout(3,1));
        holdingPanel.setBackground(lightPink);
        
        //creates and formats an enter word box, adds it to the panel
        enterWord = new JTextPane();
        enterWord.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Enter Words Found", TitledBorder.LEFT, TitledBorder.TOP));
        enterWord.setPreferredSize(new Dimension(400,278));
        enterWord.setText("");
        enterWord.setBackground(lightYellow);
        holdingPanel.add(enterWord);
        
        //creates a scroll button for the text box, adds it to the panel
        scrollPane = new JScrollPane(enterWord);
        holdingPanel.add(scrollPane);
        
        //creates and formats a label showing the time remaining in the game, adds it 
        //to the panel
        showTime = new JLabel();
        showTime.setText("      3:00");
        showTime.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Time Left", TitledBorder.LEFT, TitledBorder.TOP));
        showTime.setPreferredSize(new Dimension(400,278));
        showTime.setFont(new Font("Serif", Font.PLAIN, 70));
        holdingPanel.add(showTime);
        
        //creates and formats shake dice button, adds it to the panel
        shakeDice = new JButton();
        shakeDice.setText("Shake Dice");
        shakeDice.setFont(new Font("Serif", Font.PLAIN, 30));
        shakeDice.setBackground(extraLightpink);
        shakeDice.addActionListener(new ResetGameListener());
        holdingPanel.add(shakeDice);

        timer = new Timer(1000, new TimerListener());
        timer.start();
    
        //adds all the panels to the singular frame
        frame.add(holdCurrentword, BorderLayout.PAGE_END);
        frame.add(holdDice, BorderLayout.WEST);
        frame.add(holdingPanel, BoxLayout.Y_AXIS);
        
        //makes the frame visible
        frame.setVisible(true);
    }
        
        //exits the Scraggle Program
        private class ExitListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
               int response = JOptionPane.showConfirmDialog(frame, "Confirm to exit Scraggle?", "Exit?",
                       JOptionPane.YES_NO_OPTION);
               
               if (response == JOptionPane.YES_OPTION)
                   System.exit(0);
            }
            
        }
        
        //creates a timer countdown for the game
        private class TimerListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(minutes == 0 && seconds == 0)
                {
                    timer.stop();
                    JOptionPane.showMessageDialog(frame, "Time's up");
                }
                else
                {
                    if(seconds == 0)
                        {
                            seconds = 59;
                            minutes--;
                        }
                    else
                        seconds--;
                
                    showTime.setText("      " + minutes + ":" + seconds);

                    if(seconds < 10)
                    {
                        showTime.setText("      " + minutes + ":0" + seconds);
                    }

                
                }
            }
        }
        
        //enables and disables which tiles you can choose after you choose your first one
        private class TileListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(ae.getSource() instanceof JButton)
                {
                    JButton button = (JButton)ae.getSource();
                    
                    int row = (int)button.getClientProperty("row");
                    int col = (int)button.getClientProperty("col");
                    
                    for(int i = 0; i < 4; i++)
                    {
                        for(int j = 0; j < 4; j++)
                            dice[i][j].setEnabled(false);
                    }
                
                    if(row == 0 && col == 0)
                    {
                        dice[row + 1][col].setEnabled(true);
                        dice[row + 1][col + 1].setEnabled(true);
                        dice[row][col + 1].setEnabled(true);
                    }
                    else if (row == 3 && col == 3)
                    {
                        dice[row - 1][col].setEnabled(true);
                        dice[row - 1][col - 1].setEnabled(true);
                        dice[row][col - 1].setEnabled(true);
                    }
                    else if (row == 0 && col == 3)
                    {
                        dice[0][2].setEnabled(true);
                        dice[1][2].setEnabled(true);
                        dice[1][3].setEnabled(true);
                    }
                    else if(row == 3 && col == 0)
                    {
                        dice[3][1].setEnabled(true);
                        dice[2][0].setEnabled(true);
                        dice[2][1].setEnabled(true);
                    }
                    else if(col == 3)
                    {
                        dice[row + 1][col - 1].setEnabled(true);
                        dice[row + 1][col].setEnabled(true);
                        dice[row][col - 1].setEnabled(true);
                        dice[row - 1][col - 1].setEnabled(true);
                        dice[row - 1][col].setEnabled(true);
                    }
                    else if(row == 3)
                    {
                        dice[row][col + 1].setEnabled(true);
                        dice[row][col - 1].setEnabled(true);
                        dice[row - 1][col - 1].setEnabled(true);
                        dice[row - 1][col].setEnabled(true);
                        dice[row - 1][col + 1].setEnabled(true);
                    }
                    else if(row > 0 && col > 0)
                    {
                        dice[row - 1][col - 1].setEnabled(true);
                        dice[row - 1][col + 1].setEnabled(true);
                        dice[row - 1][col].setEnabled(true);
                        dice[row][col - 1].setEnabled(true);
                        dice[row][col + 1].setEnabled(true);
                        dice[row + 1][col - 1].setEnabled(true);
                        dice[row + 1][col].setEnabled(true);
                        dice[row + 1][col + 1].setEnabled(true);
                    }
                    else if(row > 0 && col == 0)
                    {
                        dice[row - 1][col].setEnabled(true);
                        dice[row - 1][col + 1].setEnabled(true);
                        dice[row + 1][col].setEnabled(true);
                        dice[row + 1][col + 1].setEnabled(true);
                        dice[row][col + 1].setEnabled(true); 
                    }
                    else if(row == 0 && col > 0)
                    {
                        dice[row][col + 1].setEnabled(true);
                        dice[row][col - 1].setEnabled(true);
                        dice[row + 1][col - 1].setEnabled(true);
                        dice[row + 1][col].setEnabled(true);
                        dice[row + 1][col + 1].setEnabled(true);
                    }
                    
                }
            }
        }
        
        //writes down each letter into the currentWord box and keeps track of them to form a word
        private class LetterListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                if(ae.getSource() instanceof JButton)
                {
                    JButton button = (JButton)ae.getSource();
                    String letter = (String)button.getClientProperty("letter");
                    currentWord.setText(currentWord.getText() + letter);
                }
            }
            
        }
        
        //resets the game completely (including current word, all words, score, tiles, etc.), is activated by the new game button or shake dice button
        private class ResetGameListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                score = 0;
                game.populateGrid();
                
                frame.remove(holdDice);
                holdDice.removeAll();
                
                
                //setupScraglePanel();        //FIX-ME and create method with all scraggle panel stuff
               for(int i = 0; i < 4; i++)
                {
                    for(int j = 0; j < 4; j++)
                    {
                        URL imgPath = getClass().getResource(game.getGrid()[i][j].getImgPath());
                        ImageIcon icon = new ImageIcon(imgPath);
                        dice[i][j] = new JButton(icon);
                        dice[i][j].putClientProperty("row", i);
                        dice[i][j].putClientProperty("col", j);
                        dice[i][j].putClientProperty("letter", game.getGrid()[i][j].getLetter());
                        dice[i][j].setPreferredSize(new Dimension(125,85));
                        dice[i][j].setBackground(Color.getHSBColor(196, 175, 210));
                        dice[i][j].addActionListener(new TileListener());
                        dice[i][j].addActionListener(new LetterListener());
                        holdDice.add(dice[i][j]);
                    }
                }
            
                holdDice.revalidate();
                holdDice.repaint();
                
                frame.add(holdDice, BorderLayout.WEST);
                
                enterWord.setText("");
                playerScore.setText("");
                currentWord.setText("");
                showTime.setText("      3:00");
                
                foundWords.removeAll(foundWords);
                
                timer.stop();
                minutes = 3;
                seconds = 0;
                timer.start();
            }
        }
       
        //submits the word and tries to locate it in the dictionary, awards points based on whether or not it is a valid word
        private class SubmitListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                int wordScore = game.getDictionary().search(currentWord.getText().toLowerCase());
                
                if(foundWords.contains(currentWord.getText().toLowerCase()))
                    {
                        JOptionPane.showMessageDialog(frame, "Word found already");
                    }
                else
                {
                    if(wordScore > 0)
                    {
                        enterWord.setText(enterWord.getText() + "\n" + currentWord.getText());
                        enterWord.setCaretPosition(enterWord.getDocument().getLength());
                        foundWords.add(currentWord.getText().toLowerCase());
                        score = score + wordScore;
                        playerScore.setText(String.valueOf(score));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Word not valid");
                    }
                }
                 
                currentWord.setText("");
                
                for(int i = 0; i < 4; i++)
                {
                    for(int j = 0; j < 4; j++)
                    {
                        dice[i][j].setEnabled(true);
                    }
                }
                        
            }
        }
    }