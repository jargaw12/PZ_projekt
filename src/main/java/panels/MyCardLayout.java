package panels;

import java.awt.*;

public class MyCardLayout extends CardLayout {
    private int currentCard=0;
    @Override
    public void next(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 0 ; i < ncomponents-1 ; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                currentCard = (i + 1) % ncomponents;
                comp = parent.getComponent(currentCard);
                comp.setVisible(true);
                parent.validate();
                return;
            }
        }
    }

    @Override
    public void previous(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 1 ; i < ncomponents ; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                currentCard = ((i > 0) ? i-1 : ncomponents-1);
                comp = parent.getComponent(currentCard);
                comp.setVisible(true);
                parent.validate();
                return;
            }
        }
    }

    public int getCurrentCard() {
        return currentCard;
    }
}
