package org.custom.web.factory;

import java.util.List;
import org.custom.web.valuegetter.ValueGetter;

public interface WebComponentsFactory {
  List<ValueGetter> getValueGetters();
}
