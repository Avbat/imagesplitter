package io.cell.imagesplitter;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Класс для управления разделением изображений на равные фрагменты
 */
@Service
public class SplitterImpl implements Splitter {

  private static final String VERSION = "v1";
  private static final String SPACE = "_";

  private Integer xCell0 = 1;
  private Integer yCell0 = 1;

  @Override
  public Map<String, BufferedImage> split(BufferedImage image, Integer squareSide) {
    Integer xCount = (image.getWidth() / squareSide) + (image.getWidth() % squareSide > 0 ? 1 : 0);
    Integer yCount = (image.getHeight() / squareSide) + (image.getHeight() % squareSide > 0 ? 1 : 0);

    return splitImage(image, xCount, yCount, squareSide);
  }

  @Override
  public Map<String, BufferedImage> split(BufferedImage image, Integer squareSide, Integer x0, Integer y0) {
    assert x0 > 0;
    assert y0 > 0;

    this.xCell0 = x0;
    this.yCell0 = y0;

    return split(image, squareSide);
  }

  /**
   * Разделить изображение на равные части. Граничные части будут отличаться если соотношение строон изображени отлично от 4:3
   * @param image - делимое изображение
   * @param xCount - количество фрагментов по оси X
   * @param yCount - количество фрагментов по оси Y
   * @param step - размерность стороны одного квадратного фрагмента
   * @return
   */
  private Map<String, BufferedImage> splitImage(BufferedImage image, Integer xCount, Integer yCount, Integer step) {
    int imageWeight = image.getWidth();
    int imageHeight = image.getHeight();
    Map<String, BufferedImage> imageParts = new HashMap<>();
    IntStream.rangeClosed(1, yCount).forEach(y ->
      IntStream.rangeClosed(1, xCount).forEach(x -> {
        String imageName = VERSION + SPACE + (xCell0 + x - 1) + SPACE + (yCell0 + y - 1);
        // Учет границы по X
        int nextStepWeight = (x * step <= imageWeight) ? step : (imageWeight % x);
        // Учет границы по Y
        int nextStepHeight = (y * step <= imageHeight) ? step : (imageHeight % y);
        BufferedImage part = image.getSubimage((x - 1) * step, (y - 1) * step, nextStepWeight, nextStepHeight);
        imageParts.put(imageName, part);
      })
    );
    return imageParts;
  }
}