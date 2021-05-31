package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import ru.otus.model.Measurement;

public class FileLoader<T> implements Loader {

  private final String fileName;
  private final TypeToken<T> typeToken;

  public FileLoader(String fileName, TypeToken<T> typeToken) {
    this.fileName = fileName;
    this.typeToken = typeToken;
  }

  @Override
  public List<Measurement> load() {
    try (InputStreamReader streamReader =
        new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)))) {
      return new Gson().fromJson(streamReader, this.typeToken.getType());
    } catch (IOException e) {
      throw new RuntimeException("File was not found: " + this.fileName);
    }
  }
}
