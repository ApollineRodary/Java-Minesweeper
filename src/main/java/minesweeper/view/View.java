package minesweeper.view;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import minesweeper.model.Cell;
import minesweeper.model.GameState;

public class View implements PropertyChangeListener {
    private Frame frame;
    
    public View(Frame frame) {
        this.frame = frame;
    }
    public Frame getFrame() {
        return frame;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "knownCells":
                IndexedPropertyChangeEvent ei = (IndexedPropertyChangeEvent) e;
                frame.getPanel().setContents((Cell) e.getNewValue(), ei.getIndex());
                frame.getPanel().repaint();
        
                switch ((Cell) e.getNewValue()) {
                    case FLAGGED:
                        SoundMixer.playSound(SoundEvent.PLACED_FLAG);
                        break;
                    case HIDDEN:
                        SoundMixer.playSound(SoundEvent.REMOVED_FLAG);
                        break;
                    case MINE:
                    case FALSE_FLAG:
                        break;
                    default:
                        SoundMixer.playSound(SoundEvent.REVEALED_CELL);
                        break;
                }
                break;
            
            case "minesRemaining":
                frame.getMineCounter().setValue((int) e.getNewValue());
                break;
            
            case "gameState":
                GameState state = (GameState) e.getNewValue();
                frame.getRestartButton().updateIcon(state);
                if (state==GameState.DEFEAT) announceDefeat();
                else if (state==GameState.VICTORY) announceVictory();
                break;
        }
    }

    private void announceDefeat() {
        SoundMixer.playSound(SoundEvent.REVEALED_MINE);
        JOptionPane.showMessageDialog(frame, "You lose!", "Defeat", JOptionPane.PLAIN_MESSAGE);
    }

    private void announceVictory() {
        JOptionPane.showMessageDialog(frame, "You win!", "Victory", JOptionPane.PLAIN_MESSAGE);
    }
}
