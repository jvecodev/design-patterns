package br.pucpr.table.model;

/** Observador notificado sempre que um {@link ObservableTableData} mudar. */
@FunctionalInterface
public interface TableDataListener {
  void onDataChanged(TableData data);
}
