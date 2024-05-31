package views;

import presenters.StoredInfoPresenter;

import javax.swing.*;
import java.awt.*;

public class StoredInfoView {
    private JComboBox resultComboBox;
    private JTextPane resultTextPane;
    private JScrollPane resultScrollPane;
    private JPanel contentPane;

    private StoredInfoPresenter storedInfoPresenter;
    private final JMenuItem deleteItem;
    private final JMenuItem saveItem;

    public StoredInfoView() {
        resultTextPane.setContentType("text/html");
        JPopupMenu storedInfoPopup = new JPopupMenu();
        saveItem = new JMenuItem("Save Changes!");
        deleteItem = new JMenuItem("Delete!");
        storedInfoPopup.add(saveItem);
        storedInfoPopup.add(deleteItem);
        resultTextPane.setComponentPopupMenu(storedInfoPopup);
        initListeners();
    }

    private void initListeners() {
        saveItem.addActionListener(actionEvent -> {
            storedInfoPresenter.onUpdate();
        });

        deleteItem.addActionListener(actionEvent -> {
            storedInfoPresenter.onDelete();
        });

        resultComboBox.addActionListener(actionEvent -> storedInfoPresenter.onSelectedItem());
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setStoredInfoPresenter(StoredInfoPresenter presenter) {
        storedInfoPresenter = presenter;
    }

    public void updateComboBox(Object[] items) {
        resultComboBox.setModel(new DefaultComboBoxModel(items));
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

    public boolean comboBoxHasItems() {
        return resultComboBox.getItemAt(0) != null;
    }

    public void setEnable(boolean editable) {
        resultComboBox.setEnabled(editable);
        resultTextPane.setEnabled(editable);
    }
}
