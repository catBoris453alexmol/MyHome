package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.entity.Gender;

public class GenderDaoImpl {
    private final SessionFactory factory;

    public GenderDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }



    public Gender findById(char code) {
        try(Session session = factory.openSession()){
            Gender entity = session.get(Gender.class, code);
            return entity;
        }
    }


}
