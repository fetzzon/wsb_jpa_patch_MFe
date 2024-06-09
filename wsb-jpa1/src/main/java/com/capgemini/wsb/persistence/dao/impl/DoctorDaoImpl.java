package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {
    @Override
    public List<DoctorEntity> findBySpecialization(Specialization specialization) { // DONE - napisac query
        TypedQuery<DoctorEntity> doctorEntityTypedQuery = entityManager.createQuery("SELECT d FROM DoctorEntity d where d.specialization = :specialization", DoctorEntity.class);
        doctorEntityTypedQuery.setParameter("specialization", specialization);
        return doctorEntityTypedQuery.getResultList();
    }

    @Override
    public long countNumOfVisitsWithPatient(String docFirstName, String docLastName, String patientFirstName, String patientLastName) { // DONE - napisac query
        return (long) entityManager.createQuery(
                        "SELECT COUNT(v.id) " +
                                "FROM VisitEntity v " +
                                "JOIN v.doctor d " +
                                "JOIN v.patient p " +
                                "WHERE d.firstName = :docFirstName " +
                                "AND d.lastName = :docLastName " +
                                "AND p.firstName = :patientFirstName " +
                                "AND p.lastName = :patientLastName")
                .setParameter("docFirstName", docFirstName)
                .setParameter("docLastName", docLastName)
                .setParameter("patientFirstName", patientFirstName)
                .setParameter("patientLastName", patientLastName)
                .getSingleResult();
    }



}
