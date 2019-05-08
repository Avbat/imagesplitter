package io.cell.imagesplitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true)
public class SaverImpl implements Saver {

  private static final Logger LOG = LoggerFactory.getLogger(SaverImpl.class);
  private static final String FILE_FORMAT = "jpg";

  @Value("${saver.filepath}")
  String filepath;

  @Override
  public boolean save(Map<String, BufferedImage> images) {
    List<String> savedFiles = new ArrayList<>();
    images.forEach((filename, image) -> {
      String fullName = filepath + File.separatorChar + filename + "." + FILE_FORMAT;
      File outputfile = new File(fullName);
      try {
        ImageIO.write(image, FILE_FORMAT, outputfile);
        LOG.debug("The file '{}' is saved.", fullName);
        savedFiles.add(fullName);
      } catch (IOException e) {
        LOG.error("The file '{}' could not be saved.", fullName, e);
      }
    });
    return savedFiles.size() == images.size();
  }
}