package io.cell.imagesplitter.save;

import java.util.ArrayList;
import java.util.List;

public class SaveResult {
  private boolean saved;
  private int count;
  private String message;
  private List<String> savedFiles = new ArrayList<>();

  public String getMessage() {
    return message;
  }

  public SaveResult setMessage(String message) {
    this.message = message;
    return this;
  }

  public boolean isSaved() {
    return saved;
  }

  public SaveResult setSaved(boolean saved) {
    this.saved = saved;
    return this;
  }

  public int getCount() {
    return count;
  }

  public SaveResult setCount(int count) {
    this.count = count;
    return this;
  }

  public List<String> getSavedFiles() {
    return savedFiles;
  }

  public SaveResult setSavedFiles(List<String> savedFiles) {
    this.savedFiles = savedFiles;
    return this;
  }
}
