package pl.edu.agh.sp.shoppingadvisor.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
