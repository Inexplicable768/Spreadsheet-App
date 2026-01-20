package spreadsheet;

import javax.swing.table.AbstractTableModel;
import java.io.FileWriter;
import java.io.IOException;

public class Sheet extends AbstractTableModel {
    // all implemented functions
    public String[] listOfFuncs = {
      "SUM", "MEAN", "MIN", "MAX", "MEDIAN","MODE",
      "PRODUCT", "IF", "AND", "OR", "NOT", "XOR",
      "SORT", "NOW", "RAND", "POW", "CEIL", "FLOOR",
      "FACT", "SIN", "COS", "TAN", "ASIN", "ACOS","ATAN",
      "MOD", "LN", "LOG10", "LOG2", "BIN2DEC", "DEC2BIN",
      "ISEVEN","ISODD","ISBLANK","ISTEXT","ABS","ROUND",
      "SUMIF","SQRT","NROOT","PI","SLOPE","CORREL", "STDDEV_S",
      "STDDEV_P", "COMPLEX", "RANGE", "VARIENCE","CHITEST", "RATE",
      "FV","PV","IRR","NPV","SLN"
    };
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    public int current_row;
    public int current_col;
    public Sheet(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        // to those not familiar with java this syntax may be confusing
        // youre initing the array of cells first then adding all of them
        cells = new Cell[rows][cols];
        

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell();
            }
        }
    }
    // CELL CLASS - stores data of a cell
    class Cell {
        String raw;     // "=A1+B1", "123", "hello"
        Object value;   // evaluated result
        String bgColor;   // background color in hex
        String fgColor;   // font color
        boolean bold;   // is bold or italic
        boolean italic;
        boolean strike;

        Cell() {
            raw = "";
            value = "";
        }
    }

    public void parseCommand() {
    
    }
    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }
    @Override
    public Object getValueAt(int row, int col) {
        return cells[row][col].value;
    }
    @Override
    public void setValueAt(Object value, int row, int col) {
        Cell cell = cells[row][col];
        cell.raw = value.toString();

        if (isFormula(cell.raw)) {
            cell.value = evaluateFormula(cell.raw);
        } else {
            cell.value = value;
        }

        fireTableCellUpdated(row, col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public String getColumnName(int col) {
        return String.valueOf((char) ('A' + col%26));
    }
    
    
    boolean isFormula(String s) {
        return s != null && s.startsWith("=");
    }
    
    // evaluate all formulas
    Object evaluateFormula(String formula) {
        String expr = formula.substring(1); // remove "="

        // sum multiple numbers (doubles)
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            double sum = 0;
            for (String part : parts) {
                sum += getNumericValue(part.trim());
            }
            return sum;
        }
        // sum function notation IMPORTANT - All functions split either , or : with : meaning range
        if (expr.startsWith("SUM(") && expr.endsWith(")")) {}
        if (expr.startsWith("MEAN(") && expr.endsWith(")")) {
              String range = expr.substring(expr.indexOf('(') + 1,expr.lastIndexOf(')'));
              
        }
        // subtract

        return "ERR";
    }
    double getNumericValue(String token) {
    // cell reference
    if (Character.isLetter(token.charAt(0))) {
        int[] rc = parseCellRef(token);
        Object v = cells[rc[0]][rc[1]].value;
        return Double.parseDouble(v.toString());
    }

    // number
    return Double.parseDouble(token);
}
    // take a coordinate and return a number
int[] parseCellRef(String ref) {
    ref = ref.toUpperCase();
    int col = ref.charAt(0) - 'A';
    int row = Integer.parseInt(ref.substring(1)) - 1;
    return new int[]{row, col};
}
// save as a custom file format
// the format contains a header section for formatting rules and other metadata and a data section for the cell data
public void saveSheet(String filename) {
    

}
public void loadSheet(String filename) {

}



    
}
