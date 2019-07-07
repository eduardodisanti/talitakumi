/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.framework.ui;

import java.awt.Component;
import java.util.EventObject;
import java.util.Hashtable;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author rupertus
 */
public class EachRowEditor implements TableCellEditor {
  protected Hashtable editors;

  protected TableCellEditor editor, defaultEditor;

  JTable table;

  /**
   * Constructs a EachRowEditor. create default editor
   *
   * @see TableCellEditor
   * @see DefaultCellEditor
   */
  public EachRowEditor(JTable table) {
    this.table = table;
    editors = new Hashtable();
    defaultEditor = new DefaultCellEditor(new JTextField());
    editor = defaultEditor;
  }

  /**
   * @param row
   *            table row
   * @param editor
   *            table cell editor
   */
  public void setEditorAt(int row, TableCellEditor editor) {
    editors.put(new Integer(row), editor);
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    //editor = (TableCellEditor)editors.get(new Integer(row));
    //if (editor == null) {
    //  editor = defaultEditor;
    //}
    return editor.getTableCellEditorComponent(table, value, isSelected,
        row, column);
  }

    @Override
  public Object getCellEditorValue() {
    return editor.getCellEditorValue();
  }

    @Override
  public boolean stopCellEditing() {
    return editor.stopCellEditing();
  }

    @Override
  public void cancelCellEditing() {
    editor.cancelCellEditing();
  }

    @Override
  public boolean isCellEditable(EventObject anEvent) {
    selectEditor( anEvent);
    return editor.isCellEditable(anEvent);
  }

    @Override
  public void addCellEditorListener(CellEditorListener l) {
    editor.addCellEditorListener(l);
  }

    @Override
  public void removeCellEditorListener(CellEditorListener l) {
    editor.removeCellEditorListener(l);
  }

    @Override
  public boolean shouldSelectCell(EventObject anEvent) {
    selectEditor( anEvent);
    return editor.shouldSelectCell(anEvent);
  }

  protected void selectEditor(EventObject e) {
    int row;
    
    if (e == null) {
      row = table.getSelectionModel().getAnchorSelectionIndex();
    } else {
      //row = table.rowAtPoint(e.getPoint());
      row = table.getSelectedRow();
    }
    editor = (TableCellEditor) editors.get(new Integer(row));
    if (editor == null) {
      editor = defaultEditor;
    }
  }
}

