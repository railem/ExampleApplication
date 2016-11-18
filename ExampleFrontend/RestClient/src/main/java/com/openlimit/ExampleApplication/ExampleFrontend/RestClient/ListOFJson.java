package com.openlimit.ExampleApplication.ExampleFrontend.RestClient;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListOFJson<T> implements ParameterizedType
{
  private Class<?> wrapped;

  public ListOFJson(Class<T> wrapper)
  {
    this.wrapped = wrapper;
  }

  @Override
  public Type[] getActualTypeArguments()
  {
      return new Type[] { wrapped };
  }

  @Override
  public Type getRawType()
  {
    return List.class;
  }

  @Override
  public Type getOwnerType()
  {
    return null;
  }
}