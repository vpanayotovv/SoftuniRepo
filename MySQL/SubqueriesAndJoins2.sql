use soft_uni;

select employee_id,concat(first_name,' ', last_name) as full_name,
       d.department_id , name as department_name
from departments as d join employees as e
on d.manager_id = e.employee_id
order by e.employee_id
limit 5;

select t.town_id, t.name as town_name , a.address_text
from towns as t join addresses as a
on t.town_id = a.town_id
where a.town_id in ('32','9','15')
order by town_id , address_id;

select COUNT(*)
from employees
where salary > (
    select avg(salary)
    from employees
    );

select employee_id,first_name,last_name,department_id,salary
from employees
where manager_id IS null;