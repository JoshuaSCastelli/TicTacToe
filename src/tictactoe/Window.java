/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author student
 */
public class Window {
    //declarations
    private JFrame window;
    private JPanel[] square;
    private JLabel[] marker;
    private static Random rand;
    private final int NUM_ROWS = 3;
    private final int NUM_COLS = 3;
    private Font labelFont;
    private JMenuBar menuBar;
    private JMenu optionMenu, gamePlayMenu, aboutMenu;
    private JMenuItem[] optionMenuItem;
    private JMenuItem[] gamePlayMenuItem;
    private JMenuItem aboutMenuItem;
    private boolean turn = false;
    private boolean firstTurn=false;
    private int turnCount=0;
    private int lastLocation,undo;
    
//mainMenu window
    public Window(){
        window = new JFrame("TicTacToe");
        window.setSize(400, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(new GridLayout(NUM_ROWS,NUM_COLS,5,5));
        
        
        labelFont = new Font("Arial", Font.BOLD, 100);
        
        square = new JPanel[NUM_ROWS*NUM_COLS];
        marker = new JLabel[NUM_ROWS*NUM_COLS];
        
        menuBar=new JMenuBar();
        optionMenu = new JMenu("Options");
        gamePlayMenu = new JMenu("GamePlay");
        aboutMenu = new JMenu("About");
        
        
        optionMenuItem = new JMenuItem[2];
        for(int i=0; i<optionMenuItem.length; i++){
            optionMenuItem[i] = new JMenuItem();
        }
        
        
        gamePlayMenuItem = new JMenuItem[2];
        aboutMenuItem = new JMenuItem("About Tic-Tac-Toe");
        aboutMenuItem.addActionListener(new menuItemClicker());
        gamePlayMenuItem[0] = new JMenuItem("Restart");
        gamePlayMenuItem[1] = new JMenuItem("Undo move");
        gamePlayMenuItem[0].addActionListener(new menuItemClicker());
        gamePlayMenuItem[1].addActionListener(new menuItemClicker());
        optionMenuItem[0].setText("X goes first");
        optionMenuItem[1].setText("O goes first");
        optionMenuItem[0].addActionListener(new menuItemClicker());
        optionMenuItem[1].addActionListener(new menuItemClicker());
        
        
        for(int i = 0;i<optionMenuItem.length;i++){
            optionMenu.add(optionMenuItem[i]);
        }  
        aboutMenu.add(aboutMenuItem);
        gamePlayMenu.add(gamePlayMenuItem[0]);
        gamePlayMenu.add(gamePlayMenuItem[1]);
        menuBar.add(gamePlayMenu);
        menuBar.add(optionMenu);
        menuBar.add(aboutMenu);
        
        for(int i=0;i<square.length;i++){
            square[i]=new JPanel();
            square[i].setName("" + i);
            square[i].setBackground(Color.white);
            marker[i] = new JLabel("");
            square[i].addMouseListener(new ClickListener());
            square[i].add(marker[i]);    
        }
        for(JPanel p:square){
            window.add(p);
        }
        
        window.setJMenuBar(menuBar);
        window.setVisible(true);
        
    }

    public void winCheck(){
        if(marker[6].getText().equals(marker[3].getText())&&marker[3]
                .getText().equals(marker[0].getText())&& !marker[6].
                        getText().equals("")){
            winner();  
        }
        if(marker[6].getText().equals(marker[4].getText())&&marker[4]
                .getText().equals(marker[2].getText())&& !marker[6].
                        getText().equals("")){
            winner();
        }
        if(marker[6].getText().equals(marker[7].getText())&&marker[7]
                .getText().equals(marker[8].getText())&& !marker[6].
                        getText().equals("")){
            winner();
        }
        if(marker[7].getText().equals(marker[4].getText())&&marker[4]
                .getText().equals(marker[1].getText())&& !marker[7].
                        getText().equals("")){
            winner();
        }
        if(marker[8].getText().equals(marker[5].getText())&&marker[5]
                .getText().equals(marker[2].getText())&& !marker[8].
                        getText().equals("")){
            winner();
        }
        if(marker[3].getText().equals(marker[4].getText())&&marker[4]
                .getText().equals(marker[5].getText())&& !marker[3].
                        getText().equals("")){
            winner();
        }
        if(marker[0].getText().equals(marker[1].getText())&&marker[1]
                .getText().equals(marker[2].getText())&& !marker[0].
                        getText().equals("")){
            winner();
        }
        if(marker[8].getText().equals(marker[4].getText())&&marker[4]
                .getText().equals(marker[0].getText())&& !marker[8].
                        getText().equals("")){
            winner();
        }
    }
    private void winner(){
        int x=0;
        
        x=JOptionPane.showConfirmDialog(null, "Winner!!!\nPlay again?");
        switch(x){
            case 0: 
                for(int i=0;i<marker.length;i++)
                    marker[i].setText("");
                turnCount=0;
                break;
            case 1:
                System.exit(0);
            case 2:
                System.exit(0);
        }
    }
    private void cat(){
        int x=0;
        turnCount=0;
        x=JOptionPane.showConfirmDialog(null, "Cat Nabbit!!\nPlay again?");
        switch(x){
            case 0: 
                for(int i=0;i<marker.length;i++)
                marker[i].setText("");
                break;
            case 1:
                System.exit(0);
            case 2:
                System.exit(0);
        }
        
    }
    private class ClickListener implements MouseListener{
        //on click...
        @Override
        public void mouseClicked(MouseEvent me){
            int loc = Integer.parseInt(((JPanel)(me.getSource())).getName());
            for(int i=0;i<marker.length;i++)
                marker[i].setFont(labelFont);
            if(marker[loc].getText()=="X"||marker[loc].getText()=="O"){
                JOptionPane.showMessageDialog(null, "This space is taken");
            }
            else{
                if(turn==false){
                    marker[loc].setText("X");
                    lastLocation=loc;
                    undo=0;
                }
                if(turn==true){
                    marker[loc].setText("O");
                    lastLocation=loc;
                    undo=0;
                }
                turn = !turn;
                turnCount++;
                if(turnCount>4)
                    winCheck();
                if(turnCount>8)
                    cat();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    private class menuItemClicker implements ActionListener{
        String aboutText = "Welcome to TicTacToe! The rules are simple:\n"
                         + "Each person picks a square to leave their mark\n"
                         + "First person to get THREE in a row wins!\n"
                         + "You can undo once per turn and restart anytime.\n"
                         + "Thanks for playing!";
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("X goes first")){
                if(turnCount>0){
                    JOptionPane.showMessageDialog(null, "Game in progress...");
                }
                else{
                    turn=false;
                    firstTurn=false;
                }
            }
            if(e.getActionCommand().equals("O goes first")){
                if(turnCount>0){
                    JOptionPane.showMessageDialog(null, "Game in progress...");
                }
                else{
                    turn=true;
                    firstTurn=true;
                }
            }    
            if(e.getActionCommand().equals("Restart")){
                for(int i=0;i<marker.length;i++)
                    marker[i].setText("");
                turnCount=0;
                turn=firstTurn;
            }
            if(e.getActionCommand().equals("Undo move")){
                if(undo<1){
                    marker[lastLocation].setText("");
                    turn=!turn;
                    turnCount--;
                    undo++;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Cannot undo anymore");
                }
            }   
            if(e.getActionCommand().equals("About Tic-Tac-Toe")){
                JOptionPane.showMessageDialog(null, aboutText,"About",
                        JOptionPane.INFORMATION_MESSAGE,null);
            }
        }
    }
}
    
