package io.cell.imagesplitter;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Класс для сохранения частей обработанного изображения
 */
public interface Saver {

  /**
   *
   * @param images {@link Map} c наименованием и изображением
   * @return
   */
  boolean save(Map<String, BufferedImage> images);

}