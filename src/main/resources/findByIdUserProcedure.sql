create procedure findByIdUserProcedure(
    in user_id int,
    out u_id int,
    out u_email varchar,
    out u_password varchar)

begin
select id, email, password
into u_name, u_email, u_passord
from users
where id = user_id;
end