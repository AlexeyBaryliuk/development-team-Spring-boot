package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.model.Developers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevelopersRepository extends CrudRepository<Developers, Integer>  {
}
