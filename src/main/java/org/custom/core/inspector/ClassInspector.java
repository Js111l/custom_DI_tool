package org.custom.core.inspector;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassInspector {

  private String[] packagesToScan;
  private final Logger logger = Logger.getLogger("logger");

  public ClassInspector(String... packagesToScan) {
    this.packagesToScan = packagesToScan;
  }

  public ClassInspector() {
  }

  public List<Class<?>> getAllClasses() {
    List<Class<?>> list;
    if (isNotEmpty(packagesToScan)) {
      list =
          getClassNameList().stream()
              .filter(
                  clazz ->
                      packagesToScan != null
                          && Arrays.stream(packagesToScan).anyMatch(clazz::startsWith))
              .map(this::getClass)
              .collect(toList());
    } else {
      list = getClassNameList().stream().map(this::getClass).collect(toList());
    }
    return list;
  }

  private boolean isNotEmpty(String[] packagesToScan) {
    return packagesToScan.length > 0;
  }

  private Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "An exception occurred " + e.getMessage());
      System.exit(1);
    }
    return null;
  }

  private List<String> getClassNameList() {

    var paths = getClassPaths();
    var classNames = new ArrayList<String>();

    Arrays.stream(paths)
        .forEach(
            path -> {
              var file = new File(path);
              if (file.exists() && file.isDirectory()) {
                collectClassNamesRecursively(file, classNames, new StringBuilder());
              }
            });

    return classNames;
  }

  private String[] getClassPaths() {
    var classpath = System.getProperty("java.class.path");
    return classpath.split(File.pathSeparator);
  }

  private void collectClassNamesRecursively(
      File file, List<String> classes, StringBuilder packageName) {

    if (file.isDirectory()) {
      int lastIndex = packageName.length();
      appendDirName(packageName, file);
      var directoryFiles = file.listFiles();
      assert directoryFiles != null;

      Arrays.stream(directoryFiles)
          .forEach(
              fileFromDirectory ->
                  collectClassNamesRecursively(fileFromDirectory, classes, packageName));

      packageName.delete(lastIndex, packageName.length());
    } else {
      var className = deleteClassSuffix(file.getName());
      classes.add(packageName + "." + className);
    }
  }

  private void appendDirName(StringBuilder packageName, File directory) {
    if ("main".equals(directory.getName()) || "test".equals(directory.getName())) {
      return;
    }
    var dirName = directory.getName();
    if (packageName.isEmpty()) {
      packageName.append(dirName);
    } else {
      packageName.append('.').append(dirName);
    }
  }

  private String deleteClassSuffix(String name) {
    return name.replace(".class", "");
  }
}
