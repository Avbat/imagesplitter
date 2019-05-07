package io.cell.imagesplitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SplitterImplTest {

  @Autowired
  Splitter splitter;

  @Test
  public void split_return12parts_when4x3sides() {
    BufferedImage testImage = getTestImage("/test_image_800_600.jpg");
    assertNotNull(testImage);
    Integer step = testImage.getWidth() / 4;
    Map<String, BufferedImage> imageParts = splitter.split(testImage, step);

    assertNotNull(imageParts);
    assertFalse(imageParts.isEmpty());
    assertEquals(imageParts.size(), 12);
  }

  @Test
  public void split_return15parts_when4x3sidesNotMultiple () {
    BufferedImage testImage = getTestImage("/test_image_803_600.jpg");
    assertNotNull(testImage);
    Integer step = testImage.getWidth() / 4;
    Map<String, BufferedImage> imageParts = splitter.split(testImage, step);

    assertNotNull(imageParts);
    assertFalse(imageParts.isEmpty());
    assertEquals(imageParts.size(), 15);
  }

  private BufferedImage getTestImage(String fileName) {
    try {
      URL url = this.getClass().getResource(fileName);
      return ImageIO.read(new File(url.getFile()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
    return null;
  }
}