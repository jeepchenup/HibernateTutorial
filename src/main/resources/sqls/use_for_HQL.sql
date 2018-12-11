-- USE employees TO init data

--init department
INSERT INTO department(name) SELECT dept_name FROM employees.departments;

--init employee
INSERT INTO employee(NAME, SALARY, DEPARTMENT_ID)
SELECT
    emp.first_name `NAME`,
    sala.salary `SALARY`,
    SUBSTRING_INDEX(de.dept_no, '0', - 1) `DEPARTMENT_ID`
FROM
    employees.employees emp,
    employees.dept_emp de,
    (SELECT
        emp_no, MAX(salary) salary
    FROM
        employees.salaries
    GROUP BY emp_no) sala
WHERE
    emp.emp_no = sala.emp_no
        AND emp.emp_no = de.emp_no;