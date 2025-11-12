package org.open.code.learning.base.generic;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public interface Mapper<S, T> {
    T map(S source);
}
