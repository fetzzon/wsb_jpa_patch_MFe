package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) { // DONE - napisac query
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM VisitEntity v " +
                                "JOIN v.patient p " +
                                "JOIN v.doctor d " +
                                "WHERE d.firstName = :firstName " +
                                "AND d.lastName = :lastName", PatientEntity.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) { // DONE - napisac query
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM VisitEntity v " +
                                "JOIN v.patient p " +
                                "JOIN v.medicalTreatments t " +
                                "WHERE t.type = :treatmentType", PatientEntity.class)
                .setParameter("treatmentType", treatmentType)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) { // DONE - napisac query (do sprawdzenia)
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM PatientEntity p " +
                                "JOIN p.addresses pa " +  // adresy pacjenta
                                "WHERE EXISTS (" +
                                "   SELECT 1 FROM DoctorEntity d " +
                                "   JOIN d.addresses da " +  // adresy lekarza
                                "   WHERE d.firstName = :firstName " +
                                "   AND d.lastName = :lastName " +
                                "   AND da = pa" +            // sprawdzenie
                                ")", PatientEntity.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }


    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { // DONE - napisac query
        return entityManager.createQuery(
                        "SELECT DISTINCT p FROM PatientEntity p " +
                                "LEFT JOIN p.addresses a " +
                                "WHERE a IS NULL", PatientEntity.class)
                .getResultList();
    }
}
