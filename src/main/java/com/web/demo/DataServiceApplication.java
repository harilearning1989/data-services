package com.web.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
/*@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class
		}
)*/
public class DataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataServiceApplication.class, args);
	}

	/*update employee.hr_records
	set department = (
			select department_name
	from (
					select id,
case mod(trunc(dbms_random.value(0, 10)), 10)
	when 0 then 'admin'
	when 1 then 'finance'
	when 2 then 'hr'
	when 3 then 'operations'
	when 4 then 'development'
	when 5 then 'infrastructure'
	when 6 then 'sales'
	when 7 then 'marketing'
	when 8 then 'customer'
			else 'support'
	end as department_name
	from (
					select rowid as id from employee.hr_records
			)
) t
	where t.id = employee.hr_records.rowid
);*/

}
