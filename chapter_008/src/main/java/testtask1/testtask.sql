SELECT person.name, company.name FROM person INNER JOIN company ON person.company_id = company.id WHERE person.company_id != 5;

WITH maxpeople as (SELECT company_id, count(company_id) FROM person GROUP BY (company_id) ORDER BY count DESC limit 1)
SELECT company.name, count FROM company RIGHT JOIN maxpeople ON maxpeople.company_id = company.id;