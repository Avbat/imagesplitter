package io.cell.imagesplitter.save;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Класс для сохранения частей обработанного изображения
 */
public interface Saver {

  /**
   * Сохранить изображения
   * @param images {@link Map} c наименованием и изображением
   * @return
   */
  SaveResult save(Map<String, BufferedImage> images);

  /**
   * Сформировать ответ на исключительную ситуацию
   * @param message
   * @return
   */
  SaveResult buildErrorResult(String message);

  }