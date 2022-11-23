package pack.model.api;


import java.util.List;

import pack.model.api.dto.Student;


public interface IModel {
  void run(List<Student> students) throws Exception;
}