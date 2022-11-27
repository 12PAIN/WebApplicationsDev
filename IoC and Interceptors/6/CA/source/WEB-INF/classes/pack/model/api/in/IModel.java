package pack.model.api.in;


import java.util.List;

import pack.model.api.out.IRepository;
import pack.model.api.dto.Student;


public interface IModel {
  void injectRepository(IRepository repository);
  void run(List<Student> students) throws Exception;  
}