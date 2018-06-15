package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Appointment;
import com.barbershop.bean.Users;
import com.barbershop.dao.AppointmentDao;


@Service
@Transactional
public class AppointmentService {
  @Autowired
  private AppointmentDao AppointDao;
  
  public Appointment saveAppointment(Appointment app) {
	  return  AppointDao.saveAppointment(app);
  }
  public List<Appointment> showAllAppointment(Users user,String state){
	  return AppointDao.showAllAppointment(user,state);
  }
  public List<Appointment> showNOUseAppointment(Users user,String state){
	  return AppointDao.showNOUseAppointment(user, state);
  }
  public Appointment getAppointmentById(int id ) {
	  return AppointDao.getAppointmentById(id);
  }
  public Appointment updateAppointment(Appointment appointment) {
	  return AppointDao.updateAppointment(appointment);
  }
}
