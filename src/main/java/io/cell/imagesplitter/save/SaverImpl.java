package io.cell.imagesplitter.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true)
public class SaverImpl implements Saver {

  private static final Logger LOG = LoggerFactory.getLogger(SaverImpl.class);
  private static final String FILE_FORMAT = "jpg";

  @Value("${saver.filepath}")
  String filepath;

  @Override
  public SaveResult save(Map<String, BufferedImage> images) {
    SaveResult saveResult = new SaveResult();
    images.forEach((filename, image) -> {
      String fullName = filepath + File.separatorChar + filename + "." + FILE_FORMAT;
      File outputfile = new File(fullName);
      try {
        ImageIO.write(image, FILE_FORMAT, outputfile);
        LOG.debug("The file '{}' is saved.", fullName);
        saveResult.getSavedFiles().add(filename + "." + FILE_FORMAT);
      } catch (IOException e) {
        LOG.error("The file '{}' could not be saved.", fullName, e);
      }
    });
    return saveResult
        .setCount(saveResult.getSavedFiles().size())
        .setSaved(saveResult.getSavedFiles().size() == images.size());
  }

  public SaveResult buildErrorResult(String message) {
    return new SaveResult()
        .setSaved(false)
        .setCount(0)
        .setMessage(message)
        .setSavedFiles(Collections.EMPTY_LIST);
  }
}