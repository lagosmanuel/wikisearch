package views;

import models.DataBase;
import presenters.StoredInfoPresenter;

import javax.swing.*;
import java.awt.*;

import static utils.Util.textToHtml;

public class StoredInfoViewImpl implements StoredInfoView {
    private JComboBox resultComboBox;
    private JTextPane resultTextPane;
    private JScrollPane resultScrollPane;
    private JPanel contentPane;

    private StoredInfoPresenter storedInfoPresenter;

    public StoredInfoViewImpl() {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

        resultComboBox.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));

        resultTextPane.setContentType("text/html");
        // this is needed to open a link in the browser

        resultComboBox.addActionListener(actionEvent -> resultTextPane.setText(textToHtml(DataBase.getExtract(resultComboBox.getSelectedItem().toString()))));

        JPopupMenu storedInfoPopup = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete!");
        deleteItem.addActionListener(actionEvent -> {
            if(resultComboBox.getSelectedIndex() > -1){
                DataBase.deleteEntry(resultComboBox.getSelectedItem().toString());
                resultComboBox.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
                resultTextPane.setText("");
            }
        });
        storedInfoPopup.add(deleteItem);

        JMenuItem saveItem = new JMenuItem("Save Changes!");
        saveItem.addActionListener(actionEvent -> {
            // save to DB  <o/
            DataBase.saveInfo(resultComboBox.getSelectedItem().toString().replace("'", "`"), resultTextPane.getText());  //Dont forget the ' sql problem
            //comboBox1.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
        });
        storedInfoPopup.add(saveItem);

        resultTextPane.setComponentPopupMenu(storedInfoPopup);
    }

    public Component getComponent() {
        return contentPane;
    }

    @Override
    public void setStoredInfoPresenter(StoredInfoPresenter presenter) {
        this.storedInfoPresenter = presenter;
    }
}
