//package com.thu.authorization.service;
//
//
//import com.thu.authorization.dao.ProductDao;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//
//    @InjectMocks
//    ProductService productService;
//
//    //    @Spy
//    @Mock
//    ProductDao productDao;
//
////    Employee mockEmployee;
////    Set<Project> mockProjectSet;
//
//    @BeforeEach
//    public void setup() {
//        mockEmployee = Employee.builder().id(1).fname("tracy").lastname("lan").email("tracy@gmail.com").build();
//
//        Employee mockLeader = Employee.builder().fname("leader").lastname("proj1").email("leader1@gmail.com").build();
//        mockProjectSet = new HashSet<>(Arrays.asList(Project.builder().name("project 100").leader(mockLeader).build(),
//                Project.builder().name("project 101").leader(mockLeader).build()));
//    }
//
//
//    @Test
//    @DisplayName("get employee by id success scenario")
//    public void testGetEmployeeById_success() {
//        when(employeeDAO.getEmployeeById(1)).thenReturn(mockEmployee); //means we do not actual call employeeDAO.getEmployeeById(1)
////        System.out.println(employeeDAO.spying());
////        doReturn(mockEmployee).when(employeeDAO).getEmployeeById(1); // use it while using @Spy
//        Employee employee = employeeService.getEmployeeById(1);
//        assertEquals(mockEmployee, employee);
//    }
//
//    @Test
//    public void testGetEmployeeById_failed() {
//        when(employeeDAO.getEmployeeById(-1)).thenReturn(null);
//        assertThrows(DataNotFoundException.class, () -> employeeService.getEmployeeById(-1));
//    }
//
//    @Test
//    public void testSpy() {
//        System.out.println(employeeDAO.spying());
//    }
//
//    @Test
//    public void testAddEmployee_success() {
//        when(employeeDAO.checkExistingEmail(mockEmployee.getEmail())).thenReturn(false);
//        when(employeeDAO.addEmployee(mockEmployee)).thenReturn(1);
//        assertEquals(1, employeeService.addEmployee(mockEmployee));
//    }
//
//    @Test
//    public void testAddEmployee_failedWhenEmailExists() {
//        when(employeeDAO.checkExistingEmail(mockEmployee.getEmail())).thenReturn(true);
//
//        Mockito.verify(employeeDAO, times(0)).addEmployee(mockEmployee);
//        assertThrows(DataCreationException.class, () -> employeeService.addEmployee(mockEmployee));
//    }
//
//    @Test
//    public void testGetEmployeeWithProjects_EmployeeIsFoundWithProjects() {
//        mockEmployee.setProjects(mockProjectSet);
//        when(employeeDAO.getEmployeeWithProjects(1)).thenReturn(Optional.ofNullable(mockEmployee));
//
//        EmployeeResponse employeeResponse = employeeService.getEmployeeWithProjects(1);
//
//        assertEquals(mockEmployee.getFname() + " " + mockEmployee.getLastname(), employeeResponse.getEmpFullName());
//        assertEquals(mockEmployee.getEmail(), employeeResponse.getContact());
//        assertEquals(mockEmployee.getProjects().size(), employeeResponse.getProjects().size());
//        assertTrue(employeeResponse.getServiceStatus().getSuccess());
//        mockEmployee.setProjects(null);
//    }
//
//    @Test
//    public void testGetEmployeeWithProjects_EmployeeWithNoProject() {
//        when(employeeDAO.getEmployeeWithProjects(1)).thenReturn(Optional.ofNullable(mockEmployee));
//
//        EmployeeResponse employeeResponse = employeeService.getEmployeeWithProjects(1);
//        assertEquals(mockEmployee.getFname() + " " + mockEmployee.getLastname(), employeeResponse.getEmpFullName());
//        assertEquals(mockEmployee.getEmail(), employeeResponse.getContact());
//        assertNotNull(employeeResponse.getProjects());
//        assertTrue(employeeResponse.getServiceStatus().getSuccess());
//    }
//
//    @Test
//    public void testGetEmployeeWithProjects_EmployeeIsNotFound() {
//
//        when(employeeDAO.getEmployeeWithProjects(-1)).thenReturn(Optional.empty());
//
//        EmployeeResponse employeeResponse = employeeService.getEmployeeWithProjects(-1);
//        assertNull(employeeResponse.getEmpFullName());
//        assertFalse(employeeResponse.getServiceStatus().getSuccess());
//        assertNotNull(employeeResponse.getServiceStatus().getErrorMessage());
//    }
//}