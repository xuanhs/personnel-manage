package com.xuanzjie.personnelmanage.search;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ExampleBuilder {
    private static final String separator = "_";
    private static final int IN_SIZE = 1000;
    private Example example;
    private List<String> fields;
    private List<String> sort;
    private List<String> priorities;
    private Map<String, Object> search;
    private static Field entityProperties;
    private static final String ORDER_ACS = "asc";
    private static final String ORDER_DESC = "desc";

    public ExampleBuilder(Class<?> entityClass) {
        this.example = new Example(entityClass, false);
    }

    public ExampleBuilder fields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    public ExampleBuilder fields(String... fields) {
        this.fields = Arrays.asList(fields);
        return this;
    }

    public ExampleBuilder search(Map<String, Object> search) {
        this.search = search;
        return this;
    }

    public ExampleBuilder sort(List<String> sort) {
        this.sort = sort;
        return this;
    }

    public ExampleBuilder sort(String... sort) {
        this.sort = Arrays.asList(sort);
        return this;
    }

    public ExampleBuilder priorities(String... priorities) {
        this.priorities = Arrays.asList(priorities);
        return this;
    }

    public Example build() {
        this.buildFields();
        this.buildSearch();
        this.buildSort();
        return this.example;
    }

    private Map getEntityProperty() {
        try {
            return (Map) entityProperties.get(this.example);
        } catch (IllegalAccessException var2) {
            throw new RuntimeException(var2);
        }
    }

    private String[] removeUselessFields(String[] properties) {
        Map propertyMap = this.getEntityProperty();
        if (propertyMap != null && !propertyMap.isEmpty()) {
            Stream var10000 = Stream.of(properties);
            propertyMap.getClass();
            List<String> filters = (List) var10000.filter(propertyMap::containsKey).collect(Collectors.toList());
            String[] prop = (String[]) filters.toArray(new String[filters.size()]);
            if (prop.length != properties.length) {
                log.warn("预期查询的参数字段为：{}个，而实际有效字段为：{}个，原始字段：{}，过滤后的字段：{}",
                        new Object[]{properties.length, prop.length, Arrays.toString(properties), Arrays.toString(prop)});
            }

            return prop;
        } else {
            return new String[0];
        }
    }

    private void buildFields() {
        if (this.fields != null && this.fields.size() > 0) {
            String[] properties = (String[]) this.fields.toArray(new String[this.fields.size()]);
            properties = this.removeUselessFields(properties);
            this.example.selectProperties(properties);
        }

    }

    private void buildSearch() {
        if (this.search != null && !this.search.isEmpty()) {
            long c = this.search.values().stream().filter((s) -> {
                return s != null && !"".equals(s);
            }).count();
            if (c == 0L) {
                log.warn("search value not allowed null, and stack trace:\n");
            } else {
                Example.Criteria criteria = this.example.createCriteria();
                Iterator var4 = this.priorKeys().iterator();

                while (var4.hasNext()) {
                    String key = (String) var4.next();
                    String property = key;
                    ExampleBuilder.SearchOperator operator = ExampleBuilder.SearchOperator.eq;
                    Object value = this.search.get(key);
                    int index = key.lastIndexOf("_");
                    if (index > 0) {
                        property = key.substring(0, index);
                        operator = ExampleBuilder.SearchOperator.valueOf(key.substring(index + 1));
                    }

                    switch (operator) {
                        case eq:
                            criteria.andEqualTo(property, value);
                            break;
                        case ne:
                            criteria.andNotEqualTo(property, value);
                            break;
                        case gt:
                            criteria.andGreaterThan(property, value);
                            break;
                        case gte:
                            criteria.andGreaterThanOrEqualTo(property, value);
                            break;
                        case lt:
                            criteria.andLessThan(property, value);
                            break;
                        case lte:
                            criteria.andLessThanOrEqualTo(property, value);
                            break;
                        case in:
                            criteria.andIn(property, this.getInValue(value));
                            break;
                        case notIn:
                            criteria.andNotIn(property, this.getInValue(value));
                            break;
                        case like:
                            criteria.andLike(property, this.getLikeValue(value));
                            break;
                        case notLike:
                            criteria.andNotLike(property, this.getLikeValue(value));
                            break;
                        default:
                            throw new IllegalArgumentException("search unsupport operator [" + operator + "]");
                    }
                }

            }
        } else {
            log.warn("search parameter not allowed empty, and stack trace");
        }
    }

    private String getLikeValue(Object value) {
        String strValue = (String) value;
        if (strValue != null) {
            String likeStr = strValue.replace("%", "\\%").replace("_", "\\_");
            return "%" + likeStr + "%";
        } else {
            return strValue;
        }
    }

    private List getInValue(Object value) {
        List listValue = (List) value;
        if (CollectionUtils.isEmpty(listValue)) {
            return null;
        } else {
            return listValue;
        }
    }

    private Collection<String> priorKeys() {
        if (this.priorities != null && !this.priorities.isEmpty()) {
            Set<String> keys = new HashSet(this.search.keySet());
            ArrayList<String> priorKeys = new ArrayList(keys.size());
            if (keys != null && !keys.isEmpty()) {
                Iterator var3 = this.priorities.iterator();

                while (true) {
                    while (var3.hasNext()) {
                        String priority = (String) var3.next();
                        Iterator var5 = this.search.keySet().iterator();

                        while (var5.hasNext()) {
                            String key = (String) var5.next();
                            String property = key;
                            int index = key.lastIndexOf("_");
                            if (index > 0) {
                                property = key.substring(0, index);
                            }

                            if (property.equals(priority)) {
                                priorKeys.add(key);
                                keys.remove(key);
                                break;
                            }
                        }
                    }

                    priorKeys.addAll(keys);
                    break;
                }
            }

            return priorKeys;
        } else {
            return this.search.keySet();
        }
    }

    private void buildSort() {
        if (this.sort != null && this.sort.size() > 0) {
            Iterator var1 = this.sort.iterator();

            while (var1.hasNext()) {
                String groupBy = (String) var1.next();
                String property = groupBy;
                String order = "asc";
                int index = groupBy.lastIndexOf("_");
                if (index > 0) {
                    property = groupBy.substring(0, index);
                    order = groupBy.substring(index + 1);
                }

                if ("asc".equals(order)) {
                    this.example.orderBy(property).asc();
                } else {
                    if (!"desc".equals(order)) {
                        throw new IllegalArgumentException("sort unsupport order [" + order + "]");
                    }

                    this.example.orderBy(property).desc();
                }
            }
        }

    }

    static {
        try {
            entityProperties = Example.class.getDeclaredField("propertyMap");
        } catch (NoSuchFieldException var1) {
            throw new RuntimeException(var1);
        }

        entityProperties.setAccessible(true);
    }

    private static enum SearchOperator {
        eq,
        ne,
        gt,
        gte,
        lt,
        lte,
        in,
        notIn,
        like,
        notLike;

        private SearchOperator() {
        }
    }
}
