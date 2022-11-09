package com.parkway.util;

import com.parkway.model.PageResponse;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Util {
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue instanceof Collection){
                if(((Collection<?>) srcValue).size()==0) emptyNames.add(pd.getName());
            }
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static <T> PageResponse<T> createPageResponse(Page<T> page) {
        if (page == null) {
            return new PageResponse<T>();
        }
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setData(page.getContent());
        pageResponse.setCurrentPage(page.getNumber() + 1);
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setTotalItems(page.getTotalElements());
        return pageResponse;
    }

    public static long hoursBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toHours(Math.abs(end - start));
    }
}
