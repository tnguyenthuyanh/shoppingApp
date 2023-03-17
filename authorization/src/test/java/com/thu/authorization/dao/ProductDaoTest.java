package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles(value = "test")
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

//    ProductDao mockEmployee;
//    Employee mockEmployeeWithProjects;


//    public List<Product> getAllProductsForAdmin() {
//        Session session = getCurrentSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
//        Root<Product> root = criteria.from(Product.class);
//        criteria.select(root);
//        Query query = session.createQuery(criteria);
//        List<Product> products = query.getResultList();
//        return products;
//    }

    @Test
    public void addToWatchlist() {
//        ProductRequest request = ProductRequest.builder().name("product").retail_price(12).wholesale_price(10).stock_quantity(2).build();
//        productDao.addProduct(request);
//        Product p = productDao.getProductByIdForAdmin(1);
//        System.out.println(p);

//        boolean sucess = productDao.addToWatchlist(4,1);
//
//        assertEquals(false,sucess);
    }

    @Test
    public void viewWatchListTest() {


        List<ProductResultWrapper> list = productDao.viewWatchlist(1);
        System.out.println(list.get(0).getProduct_id());
//        assertEquals();
    }


//
//    @BeforeEach
//    public void setup() {
//        Employee.EmployeeBuilder employeeBuilder = Employee.builder().fname("tracy").lastname("lan").email("tracy@gmail.com");
//        mockEmployee = employeeBuilder.build();
//
//        Employee mockLeader = Employee.builder().fname("leader").lastname("proj1").email("leader1@gmail.com").build();
//        mockEmployeeWithProjects = employeeBuilder
//                .projects(new HashSet<>(Arrays.asList(Project.builder().name("proj1").leader(mockLeader).build()))).build();
//    }
//
//
//    @Test
//    @Transactional
//    public void testGetEmployeeById_found() {
//        Integer id = employeeDAO.addEmployee(mockEmployee);
//        assertNotNull(id);
//        assertEquals(mockEmployee, employeeDAO.getEmployeeById(id));
//        mockEmployee.setId(null);
//    }
//
//    @Test
//    @Transactional
//    public void testGetEmployeeById_notFound() {
//        assertNull(employeeDAO.getEmployeeById(-1));
//    }
//
//    @Test
//    @Transactional
//    public void testCheckExistingEmail_found() {
//        employeeDAO.addEmployee(mockEmployee);
//        assertTrue(employeeDAO.checkExistingEmail(mockEmployee.getEmail()));
//    }
//
//    @Test
//    @Transactional
//    public void testCheckExistingEmail_NotFound() {
//        assertFalse(employeeDAO.checkExistingEmail(mockEmployee.getEmail()));
//    }
//
//    @Test
//    @Transactional
//    public void testGetEmployeeWithProjects_whenEmployeeWithProjects() {
//        Integer id = employeeDAO.addEmployee(mockEmployeeWithProjects);
//        mockEmployeeWithProjects.setId(id);
//        assertEquals(mockEmployeeWithProjects, employeeDAO.getEmployeeWithProjects(id).orElseGet(null));
//        mockEmployeeWithProjects.setId(null);
//    }
//
//    @Test
//    @Transactional
//    public void testGetEmployeeWithProjects_whenEmployeeNoProject() {
//        Integer id = employeeDAO.addEmployee(mockEmployee);
//        mockEmployee.setId(id);
//        assertEquals(mockEmployee, employeeDAO.getEmployeeWithProjects(id).orElseGet(null));
//        mockEmployee.setId(null);
//    }
//
//    @Test
//    @Transactional
//    public void testGetEmployeeWithProjects_whenEmployeeNotFound(){
//        assertEquals(Optional.empty(), employeeDAO.getEmployeeWithProjects(-1));
//    }
}
