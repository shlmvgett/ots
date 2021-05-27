package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage implements Cloneable {

  private List<String> data;

  public ObjectForMessage() {
  }

  public ObjectForMessage(List<String> data) {
    this.data = List.copyOf(data);
  }

  public List<String> getData() {
    return List.copyOf(data);
  }

  public void setData(List<String> data) {
    this.data = data;
  }

  @Override
  public Object clone() {
    List<String> copyData = new ArrayList<>();
    for (String str : data) {
      copyData.add(new String(str));
    }
    return new ObjectForMessage(copyData);
  }
}
