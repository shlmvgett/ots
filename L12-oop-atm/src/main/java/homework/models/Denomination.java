package homework.models;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Denomination {

  D_100(100),
  D_1000(1000),
  D_10000(10_000);

  private final Integer value;

  public static Denomination fromValue(Integer value) {
    return Arrays.stream(Denomination.values()).filter(d -> d.getValue().equals(value)).findFirst().orElseThrow();
  }
}
