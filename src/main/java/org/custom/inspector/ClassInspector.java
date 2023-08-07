package org.custom.inspector;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ClassInspector {

  private String[] packagesToScan;

  public ClassInspector(String... packagesToScan) {
    this.packagesToScan = packagesToScan;
  }

  public ClassInspector() {
  }

  public List<Class<?>> getAllClasses() {
    List<Class<?>> list;
    if (isNotEmpty(packagesToScan)) {
      list = new ArrayList<>(getClassNameList()
          .stream().filter(clazz -> packagesToScan != null && Arrays.stream(packagesToScan)
              .anyMatch(clazz::startsWith))
          .map(this::getClass).collect(toList()));
    } else {
      list = new ArrayList<>(getClassNameList()
          .stream()
          .map(this::getClass).collect(toList()));
    }
    return list;
  }

  private Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);  // TODO
    }
  }

  private List<String> getClassNameList() {

    var paths = getClassPaths();
    var classNames = new ArrayList<String>();

    Arrays.stream(paths).forEach(path -> {
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

  private void collectClassNamesRecursively(File file, List<String> classes,
      StringBuilder packageName) {

    if (file.isDirectory()) {
      int lastIndx = packageName.length();
      appendDirName(packageName, file);
      var directoryFiles = file.listFiles();
      assert directoryFiles != null;

      Arrays.stream(directoryFiles)
          .forEach(fileFromDirectory -> collectClassNamesRecursively(fileFromDirectory, classes,
              packageName));

      packageName.delete(lastIndx, packageName.length());
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
