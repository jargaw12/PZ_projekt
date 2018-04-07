package panels.myColor;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ColorComboBoxRenderer extends DefaultListCellRenderer {
    private ResourceBundle resourceBundle;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof MyColor){
            MyColor myColor = (MyColor) value;
            setForeground(Color.WHITE);
            setBackground(myColor.getDark());
            setText(resourceBundle.getString("colors."+myColor.getName()));
        }
        return this;
    }

    public ColorComboBoxRenderer(Locale locale) {
        resourceBundle= ResourceBundle.getBundle("Resource", locale);
    }
}
