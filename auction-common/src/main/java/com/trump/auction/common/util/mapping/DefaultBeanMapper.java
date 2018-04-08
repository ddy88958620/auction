package com.trump.auction.common.util.mapping;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.util.*;

/**
 * Bean映射
 * @author Owen.Yuan 2017/6/2.
 */
public class DefaultBeanMapper implements BeanMapper {

    private MapperFacade facade;

    public DefaultBeanMapper() {
        init(Collections.EMPTY_LIST);
    }

    private DefaultBeanMapper(Builder builder) {
        init(builder.callbacks);
    }

    public static class Builder {

        private List<BeanMapperBuildCallback> callbacks;

        public Builder(BeanMapperBuildCallback callback) {
            this.callbacks = new LinkedList<>();
            this.callbacks.add(callback);
        }

        public Builder withCallback(BeanMapperBuildCallback callback) {
            this.callbacks.add(callback);
            return this;
        }

        public DefaultBeanMapper build() {
            return new DefaultBeanMapper(this);
        }
    }

    private void init(List<BeanMapperBuildCallback> callbacks) {
        MapperFactory factory = buildMapperFactory();
        if (callbacks != null && !callbacks.isEmpty()) {
            for (BeanMapperBuildCallback callback : callbacks) {
                callback.callback(factory);
            }
        }
        this.facade = getMapperFacade(factory);
    }

    private MapperFacade getMapperFacade(MapperFactory factory) {
        return factory.getMapperFacade();
    }

    private MapperFactory buildMapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        return facade.map(sourceObject, destinationClass);
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass, MappingContext context) {
        return facade.map(sourceObject, destinationClass, context);
    }

    @Override
    public <S, D> void map(S sourceObject, D destinationObject) {
        facade.map(sourceObject, destinationObject);
    }

    @Override
    public <S, D> void map(S sourceObject, D destinationObject, MappingContext context) {
        facade.map(sourceObject, destinationObject, context);
    }

    @Override
    public <S, D> void map(S sourceObject, D destinationObject, Type<S> sourceType, Type<D> destinationType) {
        facade.map(sourceObject, destinationObject, sourceType, destinationType);
    }

    @Override
    public <S, D> void map(S sourceObject, D destinationObject, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        facade.map(sourceObject, destinationObject, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, destinationClass);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, destinationClass);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, destinationClass);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, destinationClass);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, destinationClass);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, destinationClass);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Class<D> destinationClass, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, destinationClass, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Class<D> destinationClass) {
        facade.mapAsCollection(source, destination, destinationClass);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Class<D> destinationClass, MappingContext context) {
        facade.mapAsCollection(source, destination, destinationClass, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Class<D> destinationClass) {
        facade.mapAsCollection(source, destination, destinationClass);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Class<D> destinationClass, MappingContext context) {
        facade.mapAsCollection(source, destination, destinationClass, context);
    }

    @Override
    public <S, D> D map(S sourceObject, Type<S> sourceType, Type<D> destinationType) {
        return facade.map(sourceObject, sourceType, destinationType);
    }

    @Override
    public <S, D> D map(S sourceObject, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        return facade.map(sourceObject, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType) {
        facade.mapAsCollection(source, destination, sourceType, destinationType);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        facade.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType) {
        facade.mapAsCollection(source, destination, sourceType, destinationType);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        facade.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass, String converterId, MappingContext mappingContext) {
        return facade.convert(source, destinationClass, converterId, mappingContext);
    }

    @Override
    public <S, D> D convert(S source, Type<S> sourceType, Type<D> destinationType, String converterId, MappingContext mappingContext) {
        return facade.convert(source, sourceType, destinationType, converterId, mappingContext);
    }

    @Override
    public <Sk, Sv, Dk, Dv> Map<Dk, Dv> mapAsMap(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<? extends Map<Dk, Dv>> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsMap(source, sourceType, destinationType);
    }

    @Override
    public <Sk, Sv, Dk, Dv> Map<Dk, Dv> mapAsMap(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<? extends Map<Dk, Dv>> destinationType, MappingContext context) {
        return facade.mapAsMap(source, sourceType, destinationType, context);
    }

    @Override
    public <S, Dk, Dv> Map<Dk, Dv> mapAsMap(Iterable<S> source, Type<S> sourceType, Type<? extends Map<Dk, Dv>> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsMap(source, sourceType, destinationType);
    }

    @Override
    public <S, Dk, Dv> Map<Dk, Dv> mapAsMap(Iterable<S> source, Type<S> sourceType, Type<? extends Map<Dk, Dv>> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsMap(source, sourceType, destinationType, context);
    }

    @Override
    public <S, Dk, Dv> Map<Dk, Dv> mapAsMap(S[] source, Type<S> sourceType, Type<? extends Map<Dk, Dv>> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsMap(source, sourceType, destinationType);
    }

    @Override
    public <S, Dk, Dv> Map<Dk, Dv> mapAsMap(S[] source, Type<S> sourceType, Type<? extends Map<Dk, Dv>> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsMap(source, sourceType, destinationType, context);
    }

    @Override
    public <Sk, Sv, D> List<D> mapAsList(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType);
    }

    @Override
    public <Sk, Sv, D> List<D> mapAsList(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <Sk, Sv, D> Set<D> mapAsSet(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType);
    }

    @Override
    public <Sk, Sv, D> Set<D> mapAsSet(Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <Sk, Sv, D> D[] mapAsArray(D[] destination, Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType);
    }

    @Override
    public <Sk, Sv, D> D[] mapAsArray(D[] destination, Map<Sk, Sv> source, Type<? extends Map<Sk, Sv>> sourceType, Type<D> destinationType, MappingContext context) {
        if (source == null) {
            return null;
        }
        return facade.mapAsArray(destination, source, sourceType, destinationType, context);
    }

}
