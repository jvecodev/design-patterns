package br.pucpr.table.model;

public class PagedTableData implements TableData {
  private final TableData data;
  private final int pageSize;
  private int page;

  public PagedTableData(TableData data, int pageSize) {
    this(data, pageSize, 0);
  }

  public PagedTableData(TableData data, int pageSize, int page) {
    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null");
    }
    if (pageSize <= 0) {
      throw new IllegalArgumentException("Page size must be positive");
    }
    this.data = data;
    this.pageSize = pageSize;
    setPage(page);
  }

  public int pageSize() {
    return pageSize;
  }

  public int pageCount() {
    return Math.max(1, (int) Math.ceil(data.rowCount() / (double) pageSize));
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    if (page < 0 || page >= pageCount()) {
      throw new IllegalArgumentException("Page out of range: " + page);
    }
    this.page = page;
  }

  public boolean hasNextPage() {
    return page + 1 < pageCount();
  }

  public boolean hasPreviousPage() {
    return page > 0;
  }

  public void nextPage() {
    if (hasNextPage()) page++;
  }

  public void previousPage() {
    if (hasPreviousPage()) page--;
  }

  @Override
  public int rowCount() {
    final var remaining = data.rowCount() - page * pageSize;
    return Math.max(0, Math.min(pageSize, remaining));
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
    return data.get(page * pageSize + row, col);
  }
}
