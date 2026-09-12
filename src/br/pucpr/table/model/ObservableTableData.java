package br.pucpr.table.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Decora qualquer {@link TableData} para permitir que a {@code Table} (ou outros componentes
 * interessados) seja avisada sempre que os dados mudarem, sem exigir mudanças na {@code Table}
 * nem na implementação decorada.
 */
public class ObservableTableData implements TableData {
  private final TableData data;
  private final List<TableDataListener> listeners = new ArrayList<>();

  public ObservableTableData(TableData data) {
    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null");
    }
    this.data = data;
  }

  public void addListener(TableDataListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    listeners.add(listener);
  }

  public void removeListener(TableDataListener listener) {
    listeners.remove(listener);
  }

  /** Deve ser chamado sempre que os dados subjacentes mudarem, para avisar os observadores. */
  public void notifyChanged() {
    for (var listener : listeners) {
      listener.onDataChanged(this);
    }
  }

  @Override
  public int rowCount() {
    return data.rowCount();
  }

  @Override
  public int colCount() {
    return data.colCount();
  }

  @Override
  public String header(int col) {
    return data.header(col);
  }

  @Override
  public String get(int row, int col) {
    return data.get(row, col);
  }
}
