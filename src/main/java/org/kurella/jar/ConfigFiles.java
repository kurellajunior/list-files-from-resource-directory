package org.kurella.jar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ConfigFiles {

  private static FileSystem jarFileSystem;

  //   lazy loading of jarFileSystem
  static synchronized private FileSystem getJarFileAsFilesystem(String specFolder) throws URISyntaxException, IOException {
    if (jarFileSystem == null) {
      jarFileSystem = FileSystems.newFileSystem(ConfigFiles.class.getResource(specFolder).toURI(), Collections.emptyMap());
    }
    return jarFileSystem;
  }

  static List<Path> listPathsFromResource(String resourceFolder, String subFolder) throws IOException, URISyntaxException {
    return Files.list(getPathForResource(resourceFolder, subFolder)).filter(Files::isRegularFile).sorted().collect(toList());
  }

  static Path getPathForResource(String resourceFolder, String filename) throws IOException, URISyntaxException {
    URL url = ConfigFiles.class.getResource(resourceFolder + "/" + filename);
    return "file".equals(url.getProtocol())
           ? Paths.get(url.toURI())
           : getJarFileAsFilesystem(resourceFolder).getPath(resourceFolder, filename);
  }
}
