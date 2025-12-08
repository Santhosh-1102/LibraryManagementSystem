package Library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Library.Entity.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

	boolean existsById(Integer id);

	Admin findByUsername(String username);

}
