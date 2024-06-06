package views;

import presenters.StoredInfoPresenter;
import utils.ImagesCache;
import utils.ParserHTML;
import utils.UIStrings;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public class StoredInfoView {
    private JComboBox comboBox;
    private JTextPane pageTextPane;
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
        pageTextPane.setContentType("text/html");
        pageTextPane.setComponentPopupMenu(storedInfoPopup);
        pageTextPane.getDocument().putProperty("imageCache", ImagesCache.getInstance().getCache());
        pageTextPane.setBackground(UIManager.getColor("TextPane.disabledBackground"));
        ((HTMLEditorKit) pageTextPane.getEditorKit()).getStyleSheet().addRule(ParserHTML.getStyleSheet());
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

    public void setPageTextPane(String text) {
        pageTextPane.setText(text);
    }

    public Object getSelectedItem() {
        return comboBox.getSelectedItem();
    }

    public String getText() {
        return pageTextPane.getText();
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }

    public boolean comboBoxHasItems() {
        return comboBox.getItemCount() > 0;
    }

    public void setEditable(boolean editable) {
        comboBox.setEnabled(editable);
        pageTextPane.setEnabled(editable);
    }
}
