package io.cell.imagesplitter.controller;

import io.cell.imagesplitter.Splitter;
import io.cell.imagesplitter.save.SaveResult;
import io.cell.imagesplitter.save.Saver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Контроллер для разделения и сохранения изображения
 */
@Controller
public class ImageSplitterController {

  private static final Logger LOG = LoggerFactory.getLogger(ImageSplitterController.class);
  private static final String FAIL_SAVING_MESSAGE = "Failed to save the resulting fragments.";
  private static final String FAIL_SPLIT_MESSAGE = "Failed to split the image.";

  private Splitter splitter;
  private Saver saver;

  @Autowired
  public ImageSplitterController(Splitter splitter, Saver saver) {
    this.splitter = splitter;
    this.saver = saver;
  }

  /**
   * Разделить изображение на квадратные области указанного размера
   *
   * @param file     изображение для деления
   * @param sideSize размерность стороны квадрата в px
   * @param x0       X координата клетки с которой начинается область для загружаемого изображения
   * @param y0       Y координата клетки с которой начинается область для загружаемого изображения
   * @return
   */
  @PostMapping(path = "/split")
  public CompletableFuture<ResponseEntity<SaveResult>> splitAndSave(@RequestParam("file") MultipartFile file,
                                                                    @RequestParam("sideSize") Integer sideSize,
                                                                    @RequestParam(name = "x0", defaultValue = "0") Integer x0,
                                                                    @RequestParam(name = "y0", defaultValue = "0") Integer y0) {
    return CompletableFuture.supplyAsync(() ->
        splitImage(file, sideSize, x0, y0))
        .thenApply(this::saveImages)
        .exceptionally(this::saveExceptionHandler);
  }

  private Map<String, BufferedImage> splitImage(MultipartFile file, Integer sideSize, Integer x0, Integer y0) {
    BufferedImage image;
    try {
      image = ImageIO.read(file.getInputStream());
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      return Collections.EMPTY_MAP;
    }
    return splitter.split(image, sideSize, x0, y0);
  }

  private ResponseEntity<SaveResult> saveImages(Map<String, BufferedImage> imageMap) {
    if (imageMap.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_MODIFIED)
          .body(saver.buildErrorResult(FAIL_SPLIT_MESSAGE));
    }
    return ResponseEntity.ok()
        .body(saver.save(imageMap));
  }

  private ResponseEntity<SaveResult> saveExceptionHandler(Throwable throwable) {
    LOG.error(throwable.getMessage(), throwable);
    return ResponseEntity
        .status(HttpStatus.NOT_MODIFIED)
        .body(saver.buildErrorResult(FAIL_SAVING_MESSAGE));
  }
}
