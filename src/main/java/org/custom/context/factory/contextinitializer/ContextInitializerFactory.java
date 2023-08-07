package org.custom.context.factory.contextinitializer;

import java.util.Map;
import org.custom.appcontext.initializer.ContextInitializer;

public interface ContextInitializerFactory {

  ContextInitializer createContextInitializer();

  Map<Class<?>, Object> getContext();
}
