package io.cell.imagesplitter;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Интерфейс для реализации разделения изображения на равные квадратные фрагменты
 */
public interface Splitter {

  /**
   * Разделить на квадраты
   * @param image
   * @param squareSide
   * @return
   */
  Map<String, BufferedImage> split(BufferedImage image, Integer squareSide);

  /**
   * Разделить на квадраты с учетом смещения координат клеток в наименовании результирующих изображений
   * @param image
   * @param squareSide
   * @param x0
   * @param y0
   * @return
   */
  Map<String, BufferedImage> split(BufferedImage image, Integer squareSide, Integer x0, Integer y0);

}