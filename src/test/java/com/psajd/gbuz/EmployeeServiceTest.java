package com.psajd.gbuz;

import com.psajd.gbuz.entities.Employee;
import com.psajd.gbuz.repositories.EmployeeRepository;
import com.psajd.gbuz.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setPosition("Developer");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFullName("Jane Smith");
        employee2.setPosition("Manager");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setPosition("Developer");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals(1L, foundEmployee.get().getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void saveEmployee_shouldReturnSavedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setPosition("Developer");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee.getId());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployee_shouldCallDeleteById() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchByFullName_shouldReturnEmployees() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setPosition("Developer");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFullName("Jane Smith");
        employee2.setPosition("Manager");

        when(employeeRepository.findByFullNameContainingIgnoreCase("John")).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.searchByFullName("John");

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findByFullNameContainingIgnoreCase("John");
    }

    @Test
    void searchByPosition_shouldReturnEmployees() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setPosition("Developer");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFullName("Jane Smith");
        employee2.setPosition("Developer");

        when(employeeRepository.findByPositionContainingIgnoreCase("Developer")).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.searchByPosition("Developer");

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findByPositionContainingIgnoreCase("Developer");
    }
}
