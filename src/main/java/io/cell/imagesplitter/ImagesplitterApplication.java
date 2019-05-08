package io.cell.imagesplitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ImagesplitterApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(ImagesplitterApplication.class, args);
  }

}
