package org.custom.core.context.factory.contextinitializer;

import java.util.Map;
import org.custom.core.appcontext.initializer.ContextInitializer;

public interface ContextInitializerFactory {

  ContextInitializer createContextInitializer();

  Map<Class<?>, Object> getContext();
}
