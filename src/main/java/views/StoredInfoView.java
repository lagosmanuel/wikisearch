package views;

import models.repos.DataBase;
import presenters.StoredInfoPresenter;
import static utils.ParserHTML.textToHtml;

import javax.swing.*;
import java.awt.*;

public class StoredInfoView implements BaseView {
    protected JComboBox resultComboBox;
    protected JTextPane resultTextPane;
    protected JScrollPane resultScrollPane;
    protected JPanel contentPane;

    protected StoredInfoPresenter storedInfoPresenter;
    protected JMenuItem deleteItem;
    protected JMenuItem saveItem;

    public StoredInfoView() {
        resultTextPane.setContentType("text/html");
        JPopupMenu storedInfoPopup = new JPopupMenu();
        saveItem = new JMenuItem("Save Changes!");
        deleteItem = new JMenuItem("Delete!");
        storedInfoPopup.add(saveItem);
        storedInfoPopup.add(deleteItem);
        resultTextPane.setComponentPopupMenu(storedInfoPopup);
        initListeners();
        updateComboBox();
    }

    private void initListeners() {
        saveItem.addActionListener(actionEvent -> {
            storedInfoPresenter.onUpdate();
        });

        deleteItem.addActionListener(actionEvent -> {
            storedInfoPresenter.onDelete();
        });

        resultComboBox.addActionListener(actionEvent -> setResultTextPane(textToHtml(DataBase.getExtract(resultComboBox.getSelectedItem().toString()))));
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setStoredInfoPresenter(StoredInfoPresenter presenter) {
        storedInfoPresenter = presenter;
    }

    public void updateComboBox() {
        resultComboBox.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
    }

    public void setResultTextPane(String text) {
        resultTextPane.setText(text);
    }

    public Object getSelectedItem() {
        return resultComboBox.getSelectedItem();
    }

    public String getText() {
        return resultTextPane.getText();
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }

    public void refreshPage() {

    }
}
