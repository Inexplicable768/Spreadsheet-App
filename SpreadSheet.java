package spreadsheet;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import java.util.ArrayList;
import java.awt.*;

public class SpreadSheet {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String[] fonts_list = {
      "Arial","Serif","Times New Roman", "Monoserrat", "Roboto", "Comic Sans", "Monotype Corsiva"
    };
    // undo / redo stuff
    private ArrayList<String> edits;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpreadSheet::createAndShowUI);
    }

    // INIT ALL THE GUI
    private static void createAndShowUI() {
        JFrame frame = new JFrame("Java Spreadsheet App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        
        // sheet class is where spreadsheet data is stored
        Sheet model = new Sheet(100, 26);
        // jtable makes actual rendering very easy
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        
                // Row header model (1..n)
        JTable rowHeader = new JTable(table.getRowCount(), 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        rowHeader.setPreferredScrollableViewportSize(new Dimension(50, 0));
        rowHeader.setRowHeight(table.getRowHeight());
        rowHeader.setDefaultRenderer(Object.class, table.getTableHeader().getDefaultRenderer());

        // Fill row numbers
        for (int i = 0; i < table.getRowCount(); i++) {
            rowHeader.setValueAt(i + 1, i, 0);
        }

        // Sync row heights
        table.addPropertyChangeListener(evt -> {
            if ("rowHeight".equals(evt.getPropertyName())) {
                rowHeader.setRowHeight(table.getRowHeight());
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(rowHeader);
        frame.add(scrollPane, BorderLayout.CENTER);

        
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu formatMenu = new JMenu("Format");
        JMenu toolsMenu = new JMenu("Tools");
        JMenu helpMenu = new JMenu("Help");
        JMenu exitMenu = new JMenu("Exit");
        
        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(viewMenu);
        menubar.add(formatMenu);
        menubar.add(toolsMenu);
        menubar.add(helpMenu);
        menubar.add(exitMenu);
        frame.setJMenuBar(menubar);
        // toolbar
        JToolBar toolBar = new JToolBar();  
        JButton undo = new JButton("←");
        JButton redo = new JButton("→");
        undo.setFocusPainted(false);
        redo.setFocusPainted(false);
        toolBar.add(undo);
        toolBar.add(redo);
        JButton printButton = new JButton("Print");
        printButton.setFocusPainted(false);
        toolBar.add(printButton);
        JButton zoom = new JButton("Zoom");
        zoom.setFocusPainted(false);
        toolBar.add(zoom);
        // fonts
        JComboBox fonts = new JComboBox(fonts_list);
        fonts.setPreferredSize(new Dimension(100,fonts.getPreferredSize().height));
        fonts.setMaximumSize(new Dimension(100,fonts.getPreferredSize().height));
        toolBar.add(fonts);
        // font size
        SpinnerModel spin = new SpinnerNumberModel(15, 1, 90, 1);     
        JSpinner spinner = new JSpinner(spin);
        spinner.setMaximumSize(new Dimension(50,25));
        toolBar.add(spinner);
        // bold, italics, strike, color
        toolBar.add(new JButton("B"));
        toolBar.add(new JButton("I"));
        toolBar.add(new JButton("S"));
        toolBar.add(new JButton("Text Color"));
        
        frame.add(toolBar, BorderLayout.NORTH);
        frame.setVisible(true);

    }
    
    
}
