package org.custom.inspector;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassInspector {

  public List<Class<?>> getAllClasses() {
    return getClassNameList()
        .stream().map(this::getClass).collect(toList());
  }

  private Class<?> getClass(String className) {
    try {
      System.out.println(className);
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private List<String> getClassNameList() {

    var paths = getClassPaths();
    var classNames = new ArrayList<String>();

    Arrays.stream(paths).forEach(path -> {
      var file = new File(path);
      if (file.exists() && file.isDirectory()) {
        getClassNamesRecursively(file, classNames, new StringBuilder());
      }
    });

    return classNames;
  }

  private String[] getClassPaths() {
    var classpath = System.getProperty("java.class.path");
    return classpath.split(File.pathSeparator);
  }

  private void getClassNamesRecursively(File fl, List<String> classes, StringBuilder packageName) {

    if (!fl.isDirectory()) {
      var className = deleteClassSuffix(fl.getName());
      classes.add(packageName + "." + className);
    } else {
      var lastIndx = packageName.length();
      appendDirName(packageName, fl);
      var files = fl.listFiles();
      assert files != null;
      Arrays.stream(files).forEach(file -> getClassNamesRecursively(file, classes, packageName));
      packageName.delete(lastIndx, packageName.length());
    }

  }

  private void appendDirName(StringBuilder packageName, File dir) {
    if (dir.getName().equals("main") || dir.getName().equals("test")) {
      return;
    }
    var dirName = dir.getName();
    if (packageName.isEmpty()) {
      packageName.append(dirName);
    } else {
      packageName.append(".").append(dirName);
    }
  }

  private String deleteClassSuffix(String name) {
    return name.replace(".class", "");
  }
}
