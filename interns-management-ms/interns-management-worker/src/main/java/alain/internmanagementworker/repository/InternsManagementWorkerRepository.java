package alain.internmanagementworker.repository;

import alain.internmanagementworker.model.Intern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternsManagementWorkerRepository extends CrudRepository<Intern,Long>{
}
