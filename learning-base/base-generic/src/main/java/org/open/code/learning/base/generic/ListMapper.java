package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class ListMapper<S, T> implements Mapper<List<S>, List<T>> {

    private final Mapper<S, T> elementMapper;

    public ListMapper(Mapper<S, T> elementMapper) {
        this.elementMapper = elementMapper;
    }


    @Override
    public List<T> map(List<S> source) {
        List<T> targetList = new ArrayList<>();
        for (S s : source) {
            targetList.add(elementMapper.map(s));
        }
        return targetList;
    }
}
