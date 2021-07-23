package ru.otus.dataprocessor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

  @Override
  public Map<String, Double> process(List<Measurement> data) {
    Map<String, Double> measurementMap = new TreeMap<>(Comparator.comparing(String::valueOf));
    for (Measurement measurement : data) {
      if (measurementMap.containsKey(measurement.getName())) {
        measurementMap.put(measurement.getName(),
            measurementMap.get(measurement.getName()) + measurement.getValue());
      } else {
        measurementMap.put(measurement.getName(), measurement.getValue());
      }
    }
    return measurementMap;
  }
}
