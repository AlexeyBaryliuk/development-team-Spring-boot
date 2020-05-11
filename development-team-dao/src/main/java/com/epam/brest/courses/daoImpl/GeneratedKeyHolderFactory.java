package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.KeyHolderFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class GeneratedKeyHolderFactory implements KeyHolderFactory {

    @Override
    public  KeyHolder newKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
