package views;

import presenters.StoredInfoPresenter;
import utils.ImagesCache;
import utils.UIStrings;

import javax.swing.*;
import java.awt.*;

public class StoredInfoView {
    private JComboBox comboBox;
    private JTextPane resultTextPane;
    private JScrollPane resultScrollPane;
    private JPanel contentPane;

    private StoredInfoPresenter storedInfoPresenter;
    private final JMenuItem deleteItem;
    private final JMenuItem saveItem;

    public StoredInfoView() {
        JPopupMenu storedInfoPopup = new JPopupMenu();
        saveItem = new JMenuItem(UIStrings.STOREDINFOVIEW_SAVEITEM_TITLE);
        deleteItem = new JMenuItem(UIStrings.STOREDINFOVIEW_DELETEITEM_TITLE);
        storedInfoPopup.add(saveItem);
        storedInfoPopup.add(deleteItem);
        resultTextPane.setContentType("text/html");
        resultTextPane.setComponentPopupMenu(storedInfoPopup);
        resultTextPane.getDocument().putProperty("imageCache", ImagesCache.getCache());
        initListeners();
    }

    private void initListeners() {
        saveItem.addActionListener(actionEvent -> storedInfoPresenter.onUpdatePage());
        deleteItem.addActionListener(actionEvent -> storedInfoPresenter.onDeletePage());
        comboBox.addActionListener(actionEvent -> storedInfoPresenter.onSelectedItem());
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setStoredInfoPresenter(StoredInfoPresenter presenter) {
        storedInfoPresenter = presenter;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateComboBox(Object[] items) {
        comboBox.setModel(new DefaultComboBoxModel(items));
    }

    public void setResultTextPane(String text) {
        resultTextPane.setText(text);
    }

    public Object getSelectedItem() {
        return comboBox.getSelectedItem();
    }

    public String getText() {
        return resultTextPane.getText();
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }

    public boolean comboBoxHasItems() {
        return comboBox.getItemAt(0) != null;
    }

    public void setEnable(boolean editable) {
        comboBox.setEnabled(editable);
        resultTextPane.setEnabled(editable);
    }
}
