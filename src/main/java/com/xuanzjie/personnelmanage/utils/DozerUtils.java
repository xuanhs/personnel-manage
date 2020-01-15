package com.xuanzjie.personnelmanage.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import java.util.List;
import java.util.stream.Collectors;

public class DozerUtils {
    private static DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Constructs new instance of destinationClass and performs mapping between from source
     *
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     * @throws MappingException
     */
    public static <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        if (source == null) {
            return null;
        }

        return mapper.map(source, destinationClass);
    }

    /**
     * Performs mapping between source and destination objects
     *
     * @param source
     * @param destination
     * @throws MappingException
     */
    public static void map(Object source, Object destination) throws MappingException {
        mapper.map(source, destination);
    }

    /**
     * Constructs new instance of destinationClass and performs mapping between from source
     *
     * @param source
     * @param destinationClass
     * @param mapId
     * @param <T>
     * @return
     * @throws MappingException
     */
    public static <T> T map(Object source, Class<T> destinationClass, String mapId) throws MappingException {
        if (source == null) {
            return null;
        }

        return mapper.map(source, destinationClass, mapId);
    }

    /**
     * Performs mapping between source and destination objects
     *
     * @param source
     * @param destination
     * @param mapId
     * @throws MappingException
     */
    public static void map(Object source, Object destination, String mapId) throws MappingException {
        mapper.map(source, destination, mapId);
    }

    /**
     * 对象集合映射
     *
     * @param sources
     * @param destinationClass
     * @param <T>
     * @return
     * @throws MappingException
     */
    public static <T> List<T> mapList(List<?> sources, Class<T> destinationClass) throws MappingException {
        return sources.stream().map(source -> mapper.map(source, destinationClass)).collect(Collectors.toList());
    }
}
