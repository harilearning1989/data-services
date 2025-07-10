package com.web.demo.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.demo.models.hospital.*;
import com.web.demo.records.AdminRecord;
import com.web.demo.records.DoctorRecord;
import com.web.demo.records.PatientRecord;
import com.web.demo.repos.hospital.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    UserRepository userRepository;
    PatientRepository patientRepository;
    ResourceLoader resourceLoader;

    DoctorRepository doctorRepository;

    AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    public DataServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    public DataServiceImpl setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    @Autowired
    public DataServiceImpl setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        return this;
    }

    @Autowired
    public DataServiceImpl setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        return this;
    }

    @Autowired
    public DataServiceImpl setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        return this;
    }

    @Autowired
    public DataServiceImpl setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        return this;
    }

    @Override
    public String saveAllPatients() throws IOException {
        List<PatientRecord> patientRecordList = getPatients("PatientDetails.json");
        Set<String> strRoles = new HashSet<>();
        strRoles.add("patient");
        List<User> userList = getPatientUserDetails(patientRecordList, strRoles);
        userRepository.saveAll(userList);

        Map<String, User> userMap = userList
                .stream()
                .collect(Collectors.toMap(User::getUsername, Function.identity()));
        List<Patient> patientList = getPatientDetails(patientRecordList, userMap);
        patientRepository.saveAll(patientList);
        return "Saved All Patients";
    }

    @Override
    public String saveAllDoctors() {
        List<DoctorRecord> doctorRecordList = getDoctors("DoctorDetails.json");
        Set<String> strRoles = new HashSet<>();
        strRoles.add("doctor");
        List<User> userList = getDoctorUserDetails(doctorRecordList, strRoles);
        userRepository.saveAll(userList);

        Map<String, User> userMap = userList
                .stream()
                .collect(Collectors.toMap(User::getUsername, Function.identity()));
        List<Doctor> doctorList = getDoctorDetails(doctorRecordList, userMap);
        doctorRepository.saveAll(doctorList);
        return "Saved All Doctors";
    }

    @Override
    public String saveAllAdmins() {
        List<AdminRecord> adminRecordList = getAdmins("AdminDetails.json");
        Set<String> strRoles = new HashSet<>();
        strRoles.add("admin");
        List<User> userList = getAdminUserDetails(adminRecordList, strRoles);
        userRepository.saveAll(userList);

        Map<String, User> userMap = userList
                .stream()
                .collect(Collectors.toMap(User::getUsername, Function.identity()));
        List<Admin> adminList = getAdminDetails(adminRecordList, userMap);
        adminRepository.saveAll(adminList);
        return "Saved All admins";
    }

    @Override
    public List<Patient> listAllPatients() {
        return patientRepository.findAll();
    }
    @Override
    public List<Doctor> listAllDoctors() {
        return doctorRepository.findAll();
    }
    @Override
    public List<Admin> listAllAdmins() {
        return adminRepository.findAll();
    }

    private List<Admin> getAdminDetails(
            List<AdminRecord> adminRecordList, Map<String, User> userMap) {
        List<Admin> patientList = adminRecordList
                .stream()
                .map(m -> {
                    String username = m.firstName() + "." + m.lastName();
                    User user = userMap.get(username.toLowerCase());

                    Address address = Address.builder()
                            .state(m.address().state())
                            .city(m.address().city())
                            .street(m.address().street())
                            .zip(Integer.parseInt(m.address().zip()))
                            .build();
                    Set<Address> addressSet = new HashSet<>();
                    addressSet.add(address);
                    Admin patient = Admin.builder()
                            .adminName(m.firstName() + " " + m.lastName())
                            .age(30)
                            .gender(m.gender())
                            .userId(user.getUserId())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .address(addressSet)
                            .build();
                    return patient;
                })
                .toList();
        return patientList;
    }

    private List<Doctor> getDoctorDetails(
            List<DoctorRecord> doctorRecordList, Map<String, User> userMap) {
        List<Doctor> doctorList = doctorRecordList
                .stream()
                .map(m -> {
                    String username = m.firstName() + "." + m.lastName();
                    User user = userMap.get(username.toLowerCase());

                    Address address = Address.builder()
                            .state(m.address().state())
                            .city(m.address().city())
                            .street(m.address().street())
                            .zip(Integer.parseInt(m.address().zip()))
                            .build();
                    Set<Address> addressSet = new HashSet<>();
                    addressSet.add(address);
                    Doctor patient = Doctor.builder()
                            .doctorName(m.firstName() + " " + m.lastName())
                            .age(30)
                            .userId(user.getUserId())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .address(addressSet)
                            .build();
                    return patient;
                })
                .toList();
        return doctorList;
    }

    private List<User> getDoctorUserDetails(
            List<DoctorRecord> doctorRecordList, Set<String> strRoles) {
        List<User> userList = doctorRecordList
                .stream()
                .map(m -> {
                    Set<Role> roleSet = getUserRole(strRoles);
                    String username = m.firstName() + "." + m.lastName();
                    User user = User.builder()
                            .username(username.toLowerCase())
                            .email(m.email())
                            .password("$2a$10$F5ca4JlJ8DJlreFefmRV.uQRB0VZ9cSi0mwapxt1riJtQgtAes/PC")
                            .phone(m.phone())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .roles(roleSet)
                            .build();
                    return user;
                })
                .toList();
        return userList;
    }

    private List<User> getAdminUserDetails(
            List<AdminRecord> adminRecordList, Set<String> strRoles) {
        List<User> userList = adminRecordList
                .stream()
                .map(m -> {
                    Set<Role> roleSet = getUserRole(strRoles);
                    String username = m.firstName() + "." + m.lastName();
                    User user = User.builder()
                            .username(username.toLowerCase())
                            .email(m.email())
                            .password("$2a$10$F5ca4JlJ8DJlreFefmRV.uQRB0VZ9cSi0mwapxt1riJtQgtAes/PC")
                            .phone(m.phone())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .roles(roleSet)
                            .build();
                    return user;
                })
                .toList();
        return userList;
    }

    private List<AdminRecord> getAdmins(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = null;
        List<AdminRecord> adminRecordList = null;
        try {
            inputStream = resourceLoader.getResource("classpath:" + fileName).getInputStream();
            adminRecordList = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminRecordList;
    }

    private List<DoctorRecord> getDoctors(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = null;
        List<DoctorRecord> doctorRecordList = null;
        try {
            inputStream = resourceLoader.getResource("classpath:" + fileName).getInputStream();
            doctorRecordList = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doctorRecordList;
    }

    private List<PatientRecord> getPatients(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = null;
        List<PatientRecord> patients = null;
        try {
            inputStream = resourceLoader.getResource("classpath:" + fileName).getInputStream();
            patients = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    private List<User> getPatientUserDetails(List<PatientRecord> patientList, Set<String> strRoles) {
        List<User> userList = patientList
                .stream()
                .map(m -> {
                    Set<Role> roleSet = getUserRole(strRoles);
                    String username = m.firstName() + "." + m.lastName();
                    User user = User.builder()
                            .username(username.toLowerCase())
                            .email(m.email())
                            .password("$2a$10$F5ca4JlJ8DJlreFefmRV.uQRB0VZ9cSi0mwapxt1riJtQgtAes/PC")
                            .phone(m.phone())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .roles(roleSet)
                            .build();
                    return user;
                })
                .toList();
        return userList;
    }

    private List<Patient> getPatientDetails(List<PatientRecord> patientRecordList, Map<String, User> userMap) {
        List<Patient> patientList = patientRecordList
                .stream()
                .map(m -> {
                    String username = m.firstName() + "." + m.lastName();
                    User user = userMap.get(username.toLowerCase());

                    Address address = Address.builder()
                            .state(m.address().state())
                            .city(m.address().city())
                            .street(m.address().street())
                            .zip(Integer.parseInt(m.address().zip()))
                            .build();
                    Set<Address> addressSet = new HashSet<>();
                    addressSet.add(address);
                    Patient patient = Patient.builder()
                            .patientName(m.firstName() + " " + m.lastName())
                            .age(30)
                            .gender(m.gender())
                            .dob(new Date())
                            .userId(user.getUserId())
                            .createdDate(new Date())
                            .updatedDate(new Date())
                            .address(addressSet)
                            .build();
                    return patient;
                })
                .toList();
        return patientList;
    }

    private Set<Role> getUserRole(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "patient":
                        Role patientRole = roleRepository.findByName(RoleName.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(patientRole);

                        break;
                    case "doctor":
                        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(doctorRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }
}
