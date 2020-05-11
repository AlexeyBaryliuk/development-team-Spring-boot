package com.epam.brest.courses.dao;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {
   KeyHolder newKeyHolder();
}
