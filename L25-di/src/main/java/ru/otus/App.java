package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.services.GameProcessor;

public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

    GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
//    GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
//    GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

    gameProcessor.startGame();
  }
}
