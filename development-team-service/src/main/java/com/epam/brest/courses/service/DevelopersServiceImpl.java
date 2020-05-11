package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DevelopersJdbcDao;
import com.epam.brest.courses.model.Developers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevelopersServiceImpl implements DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersServiceImpl.class);

    private final DevelopersJdbcDao developersJdbcDao;

    public DevelopersServiceImpl(DevelopersJdbcDao developersJdbcDao) {
        this.developersJdbcDao = developersJdbcDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developers> findAll() {

        LOGGER.debug("findAll()");
        return developersJdbcDao.findAll();
    }

    @Override
    public Optional<Developers> findById(Integer developerId) {

        LOGGER.debug("findById developerId = {}", developerId);
        return developersJdbcDao.findById(developerId);
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("create() developer = {}", developer);
        return developersJdbcDao.create(developer);
    }

    @Override
    public Integer update(Developers developer) {

        LOGGER.debug("update() developer = {}", developer);
        return developersJdbcDao.update(developer);
    }

    @Override
    public Integer delete(Integer developerId) {

        LOGGER.debug("delete() developerId = {}", developerId );
        return developersJdbcDao.delete(developerId);
    }

}
