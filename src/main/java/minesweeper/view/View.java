package minesweeper.view;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
                    case MINE:
                        SoundMixer.playSound(SoundEvent.REVEALED_MINE);
                        break;
                    case FLAGGED:
                        SoundMixer.playSound(SoundEvent.PLACED_FLAG);
                        break;
                    case HIDDEN:
                        SoundMixer.playSound(SoundEvent.REMOVED_FLAG);
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
                frame.getRestartButton().updateIcon((GameState) e.getNewValue());
                break;
        }
    }
}
