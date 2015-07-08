package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ImageSnippet;
import kinderuni.pictureEditor.ThreadSaveImageSnippetContainer;
import kinderuni.pictureEditor.language.Language;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by markus on 26.06.15.
 */
public class AnimationTablePanel extends JPanel {
    private JTable animationTable;
    private JButton leftButton;
    private JButton downButton;
    private JButton deleteButton;
    private Dimension imageIconSize = new Dimension(100, 100);
    private int selectedImageIndex = 0;
    private AnimationTableModel tableModel;
    private ThreadSaveImageSnippetContainer imageSnippetContainer;
    private DetailPanelCallback detailPanelCallback;
    private boolean leftButtonAction = false, rightButtonAction = false, deleteButtonAction = false;

    public AnimationTablePanel() {
        this.imageSnippetContainer = ThreadSaveImageSnippetContainer.getInstance();
        this.tableModel = new AnimationTableModel(imageSnippetContainer);

        this.setLayout(new BoxLayout(this, this.getWidth()));

        this.leftButton = new JButton(Language.MOVE_LEFT);
        this.downButton = new JButton(Language.MOVE_RIGHT);
        this.deleteButton = new JButton(Language.DELETE);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedImageIndex = tableModel.moveLeft(selectedImageIndex);
                updateTableSelection();
                setColumnWith();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedImageIndex = tableModel.moveRight(selectedImageIndex);
                updateTableSelection();
                setColumnWith();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedImageIndex = tableModel.delete(selectedImageIndex);
                updateTableSelection();
                setColumnWith();
            }
        });

        this.animationTable = new JTable(tableModel);
        animationTable.setCellSelectionEnabled(true);
        animationTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        animationTable.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && animationTable.getSelectedColumn() >= 0) {
                    selectedImageIndex = animationTable.getSelectedColumn();
                    callDetailPanelCallback();
                }
            }
        });
        animationTable.setRowSelectionAllowed(false);
        System.err.println("Size of imageSnippetContainer: " + imageSnippetContainer.size());
        animationTable.setRowHeight(((ImageIcon) tableModel.getValueAt(0, 0)).getIconHeight() + 10);
        setColumnWith();

        JPanel tableContainer = new JPanel();
        tableContainer.add(animationTable);
        JScrollPane scrollPane = new JScrollPane(tableContainer);
        JScrollBar horizontalScrollBar = scrollPane.createHorizontalScrollBar();
        scrollPane.setHorizontalScrollBar(horizontalScrollBar);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        Box horizontalBox = Box.createHorizontalBox();
        Box verticalBox = Box.createVerticalBox();
        horizontalBox.add(leftButton);
        horizontalBox.add(downButton);
        horizontalBox.add(deleteButton);
        verticalBox.add(scrollPane);
        verticalBox.add(horizontalBox);
        this.add(verticalBox);

        this.addKeyListener(SaveKeyListener.getInstance());
    }

    private void setColumnWith() {
        for (int i=0; i<animationTable.getColumnModel().getColumnCount(); i++) {
            animationTable.getColumnModel().getColumn(i).setWidth((int) imageIconSize.getWidth());
            animationTable.getColumnModel().getColumn(i).setMinWidth((int) imageIconSize.getWidth());
            animationTable.getColumnModel().getColumn(i).setMaxWidth((int) imageIconSize.getWidth());
            animationTable.getColumnModel().getColumn(i).setPreferredWidth((int) imageIconSize.getWidth());
        }
    }

    public void init() {
//        animationTable.setRowHeight(((ImageIcon)tableModel.getValueAt(0, 0)).getIconHeight()+10);
    }

    private void updateTableSelection() {
        if (selectedImageIndex >= 0 && selectedImageIndex < imageSnippetContainer.size()) {
            animationTable.clearSelection();
            animationTable.addColumnSelectionInterval(selectedImageIndex, selectedImageIndex);
            animationTable.addRowSelectionInterval(0, 0);
        }
    }

    public void setDetailPanelCallback(DetailPanelCallback callback) {
        this.detailPanelCallback = callback;
    }

    public void callDetailPanelCallback() {
        this.detailPanelCallback.changeSnipped(selectedImageIndex);
    }

    private class AnimationTableModel extends AbstractTableModel {
        private ThreadSaveImageSnippetContainer imageSnippetContainer;
//        private static final int COLUMS = 1;
        private static final int ROWS = 1;

        public AnimationTableModel(ThreadSaveImageSnippetContainer imageSnippetContainer) {
            this.imageSnippetContainer = imageSnippetContainer;
        }

        @Override
        public int getRowCount() {
            return ROWS;
        }

        @Override
        public int getColumnCount() {
            return imageSnippetContainer.size();
        }

        @Override
        public String getColumnName(int columnIndex) {
            return "" + (columnIndex+1);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return ImageIcon.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return imageSnippetContainer.get(columnIndex).getDefaultSizeImageIcon();
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        public int moveLeft(int index) {
            if (index < 1 || index > imageSnippetContainer.size()) {
                return index;
            }
            try {
                ImageSnippet tmp = imageSnippetContainer.get(index - 1);
                imageSnippetContainer.set(index - 1, imageSnippetContainer.get(index));
                imageSnippetContainer.set(index, tmp);
                fireTableStructureChanged();
                return --index;
            } catch (NoSuchElementException e) {
                return index;
            }
        }

        public int moveRight(int index) {
            if (index < 0 || index >= imageSnippetContainer.size()-1) {
                return index;
            }
            ImageSnippet tmp = imageSnippetContainer.get(index+1);
            imageSnippetContainer.set(index+1, imageSnippetContainer.get(index));
            imageSnippetContainer.set(index, tmp);
            fireTableStructureChanged();
            return ++index;
        }

        public int delete(int index) {
            if (index < 0 || index > imageSnippetContainer.size()) {
                return index;
            }
            imageSnippetContainer.remove(index);
            fireTableStructureChanged();
            if (index == imageSnippetContainer.size()) {
                return index-1;
            }
            return index;
        }
    }

}
