package com.epam.brest.courses.utils;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.soap_api.DeveloperInfo;

public class DevelopersUtils {

    public static DeveloperInfo convertDevelopersToDevelopersInfo(Developers developer){

        DeveloperInfo developerInfo = new DeveloperInfo();

        developerInfo.setDeveloperId(developer.getDeveloperId());
        developerInfo.setFirstName(developer.getFirstName());
        developerInfo.setLastName(developer.getLastName());

        return developerInfo;
    }

    public static Developers convertDeveloperInfoToDevelopers(DeveloperInfo developerInfo) {

        Developers developer = new Developers();

        developer.setDeveloperId(developerInfo.getDeveloperId());
        developer.setFirstName(developerInfo.getFirstName());
        developer.setLastName(developerInfo.getLastName());

        return developer;
    }
}
