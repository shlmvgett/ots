package ru.otus.dataprocessor;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {

  private final String fileName;

  public FileSerializer(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void serialize(Map<String, Double> data) {
    var json = new Gson().toJson(data);
    try {
      Files.writeString(Paths.get(this.fileName), json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
