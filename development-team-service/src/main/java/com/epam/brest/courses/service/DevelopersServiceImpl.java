package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.myBatis.DevelopersMyBatis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevelopersServiceImpl implements DevelopersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersServiceImpl.class);

    private final DevelopersMyBatis developersDao;
    @Autowired
    DevelopersMyBatis developersMyBatis;

    @Autowired
    public DevelopersServiceImpl(DevelopersMyBatis developersDao) {
        this.developersDao = developersDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developers> findAll() {

        LOGGER.debug("findAll()");
        return developersDao.findAll();
    }

    @Override
    public Optional<Developers> findByDeveloperId(Integer developerId) {

        LOGGER.debug("findByDeveloperIddeveloperId = {}", developerId);
        return developersDao.findByDeveloperId(developerId);
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("create() developer = {}", developer);
        return developersDao.create(developer);
    }

    @Override
    public Integer update(Developers developer) {

        LOGGER.debug("update() developer = {}", developer);
        return developersDao.update(developer);
    }

    @Override
    public Integer deleteByDeveloperId(Integer developerId) {

        LOGGER.debug("delete() developerId = {}", developerId );
        return developersDao.deleteByDeveloperId(developerId);
    }

}
