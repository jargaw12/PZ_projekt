package panels;

import configuration.Base;
import configuration.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Table extends JPanel {
    private Base baza = new Base();
    private DefaultTableModel model = new DefaultTableModel();
    public JTable table = new JTable(model);
    private ResourceBundle resourceBundle;


    public void read() {
        String sql = "SELECT TOP 20 * FROM wyniki ORDER BY score ASC ";
        ResultSet resultSet = null;
        try {
            baza.polacz();
            resultSet = baza.zapytanie(sql);
            String[] tokens = null;
            model.addColumn(resourceBundle.getString("player"));
            model.addColumn(resourceBundle.getString("score"));
            model.addColumn(resourceBundle.getString("date"));
            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),resultSet.getString(3)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getInstance().logger.log(Level.WARNING,"Błąd połączenia z bazą danych",e);
        } finally {
            baza.rozlacz();
        }
    }

    public Table(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource",locale);
    }

    public void refresh(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        model.setColumnIdentifiers(new Object[]{resourceBundle.getString("player"),resourceBundle.getString("score"),resourceBundle.getString("date")});
    }

}

