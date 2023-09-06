package org.custom.web.factory;

import java.util.List;
import org.custom.web.valuegetter.DefaultPathVariablesGetter;
import org.custom.web.valuegetter.DefaultRequestParamGetter;
import org.custom.web.valuegetter.ValueGetter;

public class DefaultComponentsFactory implements WebComponentsFactory {

  @Override
  public List<ValueGetter> getValueGetters() {
    return List.of(new DefaultPathVariablesGetter(), new DefaultRequestParamGetter());
  }
}
