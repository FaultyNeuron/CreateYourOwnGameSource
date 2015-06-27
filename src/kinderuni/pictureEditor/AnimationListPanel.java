package kinderuni.pictureEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by markus on 26.06.15.
 */
public class AnimationListPanel extends JPanel {
    private JTable animationTable;
    private JButton leftButton;
    private JButton downButton;
    private JButton deleteButton;
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private Dimension imageIconSize = new Dimension(100, 100);
    private int selectedImageIndex = 0;
    private AnimationTableModel tableModel = new AnimationTableModel();

    public AnimationListPanel() {
        this.setLayout(new BoxLayout(this, this.getWidth()));
        createImages();
        createImages();

        this.leftButton = new JButton(Language.MOVE_LEFT);
        this.downButton = new JButton(Language.MOVE_RIGHT);
        this.deleteButton = new JButton(Language.DELETE);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("leftButton clicked: index=" + selectedImageIndex);
                animationTable.clearSelection();
                selectedImageIndex = tableModel.moveLeft(selectedImageIndex);
                animationTable.addColumnSelectionInterval(selectedImageIndex, selectedImageIndex);
                animationTable.addRowSelectionInterval(0, 0);
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("downButton clicked: index=" + selectedImageIndex);
                animationTable.clearSelection();
                selectedImageIndex = tableModel.moveRight(selectedImageIndex);
                animationTable.addColumnSelectionInterval(selectedImageIndex, selectedImageIndex);
                animationTable.addRowSelectionInterval(0, 0);

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("deleteButton clicked: index=" + selectedImageIndex);
                animationTable.clearSelection();
                selectedImageIndex = tableModel.delete(selectedImageIndex);
                System.err.println("deleteButton clicked: index=" + selectedImageIndex);
                animationTable.addColumnSelectionInterval(selectedImageIndex, selectedImageIndex);
                animationTable.addRowSelectionInterval(0, 0);
            }
        });

        for (Image image : images) {
            tableModel.add(new ImageIcon(image.getScaledInstance((int) imageIconSize.getWidth(), (int) imageIconSize.getHeight(), Image.SCALE_SMOOTH)));
        }

        this.animationTable = new JTable(tableModel);
        animationTable.setCellSelectionEnabled(true);
        animationTable.setSelectionModel(new AnimationTableSelectionModel());
        animationTable.setRowSelectionAllowed(false);
        animationTable.addMouseListener(new AnimationTableMouseListener());

//        for (int i=0; i<animationTable.getColumnModel().getColumnCount(); i++) {
//            animationTable.getColumnModel().getColumn(i).setWidth((int) imageIconSize.getWidth());
//            animationTable.getColumnModel().getColumn(i).setMinWidth((int) imageIconSize.getWidth());
//            animationTable.getColumnModel().getColumn(i).setMaxWidth((int) imageIconSize.getWidth());
//            animationTable.getColumnModel().getColumn(i).setPreferredWidth((int) imageIconSize.getWidth());
//        }
        animationTable.setRowHeight(((ImageIcon)tableModel.getValueAt(0, 0)).getIconHeight()+10);

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
    }

    private void createImages() {
        String path = "/home/markus/uni/kinderuni/kinderuni/resources/animations/player/walking/anim%d.jpg";
        for (int i=0; i<14; i++) {
            String fullPath = String.format(path, i);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(fullPath));
            } catch (IOException e) {
                System.err.println("Could not read image " + fullPath);
            }
            if (image != null) {
                images.add(image);
            }
        }
    }

    private class AnimationTableMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedImageIndex = animationTable.getSelectedColumn();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class AnimationTableSelectionModel extends DefaultListSelectionModel {
        public AnimationTableSelectionModel () {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    private class AnimationTableModel extends AbstractTableModel {
        private ArrayList<ImageIcon> data = new ArrayList<>();
//        private static final int COLUMS = 1;
        private static final int ROWS = 1;

        @Override
        public int getRowCount() {
            return ROWS;
        }

        @Override
        public int getColumnCount() {
            return data.size();
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
            return data.get(columnIndex);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (aValue instanceof ImageIcon) {
                if (columnIndex < data.size()) {
                    data.add(rowIndex, (ImageIcon)aValue);
                } else {
                    data.add((ImageIcon)aValue);
                }
            }
        }

        public void add(ImageIcon icon) {
            data.add(icon);
        }

        public int moveLeft(int index) {
            if (index < 1 || index > data.size()) {
                return index;
            }
            ImageIcon tmp = data.get(index-1);
            data.set(index-1, data.get(index));
            data.set(index, tmp);
            fireTableStructureChanged();

            return --index;
        }

        public int moveRight(int index) {
            if (index < 0 || index > data.size()-1) {
                return index;
            }
            ImageIcon tmp = data.get(index+1);
            data.set(index+1, data.get(index));
            data.set(index, tmp);
            fireTableStructureChanged();
            return ++index;
        }

        public int delete(int index) {
            if (index < 0 || index > data.size()) {
                return index;
            }
            data.remove(index);
            fireTableStructureChanged();
            if (index == data.size()) {
                return index-1;
            }
            return index;
        }
    }

}
